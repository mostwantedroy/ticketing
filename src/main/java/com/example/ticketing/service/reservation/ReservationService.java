package com.example.ticketing.service.reservation;

import com.example.ticketing.client.PaymentGatewayApiClient;
import com.example.ticketing.client.dto.PaymentCancelRequest;
import com.example.ticketing.client.dto.PaymentCompleteRequest;
import com.example.ticketing.client.dto.PaymentCompletedDto;
import com.example.ticketing.common.ErrorCode;
import com.example.ticketing.domain.Performance;
import com.example.ticketing.domain.Reservation;
import com.example.ticketing.domain.ReservationSeat;
import com.example.ticketing.domain.VenueSeat;
import com.example.ticketing.domain.Venue;
import com.example.ticketing.entity.ReservationEntity;
import com.example.ticketing.entity.PerformanceEntity;
import com.example.ticketing.entity.ReservationSeatEntity;
import com.example.ticketing.exception.ReservationException;
import com.example.ticketing.repository.performance.PerformanceRepository;
import com.example.ticketing.repository.reservation.ReservationRepository;
import com.example.ticketing.service.performance.PerformanceService;
import com.example.ticketing.service.reservation.dto.ReservationRequest;
import com.example.ticketing.type.VenueSeatType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.example.ticketing.common.ErrorCode.RESERVATION_SEAT_EXCEED_LIMIT;
import static com.example.ticketing.common.ErrorCode.RESERVATION_SEAT_OCCUPIED;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;
    private final PerformanceService performanceCapacityService;
    private final PaymentGatewayApiClient paymentGatewayApiClient;
    private final ApplicationEventPublisher eventPublisher;

    private final RedissonClient redisson;
    private final ApplicationContext applicationContext;

    private static final Integer MAX_RESERVABLE_SEATS = 5;

    public Reservation reserve(Long userId, Long performanceId, ReservationRequest reservationRequest) {
        final String reservationLockKey = "PERFORMANCE:" + performanceId;
        final RLock lock = redisson.getLock(reservationLockKey);

        try {
            if (lock.tryLock(5000, 4000, TimeUnit.MILLISECONDS)) {
                final ReservationService transactionAppliedReservationService = applicationContext.getBean(ReservationService.class);

                final Reservation reservation = transactionAppliedReservationService.startReserveTransaction(userId, performanceId, reservationRequest);

                return reservation;
            } else {
                throw new ReservationException(ErrorCode.ANOTHER_RESERVATION_PROCESSING);
            }
        } catch (InterruptedException exception) {
            throw new ReservationException("UNKNOWN Exception");
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
    }

    public Reservation startReserveTransaction(Long userId, Long performanceId, ReservationRequest reservationRequest) {
        if (Objects.isNull(reservationRequest)) {
            throw new ReservationException("empty reservation request");
        }

        final Performance performance = performanceRepository.findById(performanceId)
                .map(PerformanceEntity::toDomain)
                .orElseThrow(() -> new ReservationException("not found performance id:" + performanceId));

        validateEligibleForReservation(performance, reservationRequest);
        performanceCapacityService.deleteCapacity(performanceId, reservationRequest.getRequestedReservationSeatCount());

        final Reservation reservation = createReservation(userId, performance, reservationRequest);

        paymentGatewayApiClient.completePayment(PaymentCompleteRequest.builder()
                .userId(userId)
                .requestAmount(reservation.getTotalPrice())
                .paymentInfo(reservation.getPaymentInfo())
                .build());

        eventPublisher.publishEvent(PaymentCompletedDto.builder()
                .userId(userId)
                .requestAmount(reservation.getTotalPrice())
                .paymentInfo(reservation.getPaymentInfo())
                .build());

        return reservationRepository.save(ReservationEntity.fromDomain(reservation)).toDomain();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void cancelPayment(PaymentCompletedDto paymentCompletedDto) {
        paymentGatewayApiClient.cancelPayment(PaymentCancelRequest.builder()
                .userId(paymentCompletedDto.getUserId())
                .paymentInfo(paymentCompletedDto.getPaymentInfo())
                .requestAmount(paymentCompletedDto.getRequestAmount())
                .build());
    }

    private Reservation createReservation(Long userId, Performance performance, ReservationRequest reservationRequest) {
        final List<Long> seatIds = reservationRequest.getReservationSeatIds();
        final Venue venue = performance.getVenue();
        final Map<Long, VenueSeatType> venueSeatTypeBySeatId = venue.getVenueSeatTypeBySeatId();
        final BigDecimal totalPrice = calculateTotalPrice(performance, seatIds);

        final List<ReservationSeat> reservationSeats = seatIds.stream()
                .map(seatId -> ReservationSeat.create()
                        .venueSeatId(seatId)
                        .seatType(venueSeatTypeBySeatId.get(seatId))
                        .build())
                .collect(Collectors.toList());

        return Reservation.create()
                .userId(userId)
                .performanceId(performance.getPerformanceId())
                .paymentInfo(reservationRequest.getPaymentInfo())
                .totalPrice(totalPrice)
                .vipPrice(performance.getVipPrice())
                .normalPrice(performance.getNormalPrice())
                .reservationSeats(reservationSeats)
                .build();
    }

    @Transactional(readOnly = true)
    public List<ReservationSeat> getAllReservationSeat(Long performanceId) {
        return reservationRepository.findAllReservationSeatsByPerformanceId(performanceId)
                .stream()
                .map(ReservationSeatEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReservationSeat> getReservationSeatBy(List<Long> venueSeatIds) {
        return reservationRepository.findByVenueSeatIds(venueSeatIds)
                .stream()
                .map(ReservationSeatEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Reservation getReservation(Long reservationId) {
        return reservationRepository.findByReservationId(reservationId)
                .map(ReservationEntity::toDomain)
                .orElseThrow(() -> new ReservationException(ErrorCode.NOT_FOUND_ENTITY));
    }

    private BigDecimal calculateTotalPrice(Performance performance, List<Long> seatIds) {
        if (CollectionUtils.isEmpty(seatIds)) {
            return BigDecimal.ZERO;
        }

        final Venue venue = performance.getVenue();
        final Map<Long, VenueSeatType> venueSeatTypeMap = venue.getVenueSeats()
                .stream()
                .collect(Collectors.toMap(VenueSeat::getSeatId, VenueSeat::getSeatType, (x1, x2) -> x1));

        final long totalNormalSeatCount = seatIds.stream()
                .map(venueSeatTypeMap::get)
                .filter(Objects::nonNull)
                .filter(VenueSeatType.NORMAL::equals)
                .count();

        final long totalVipSeatCount = seatIds.stream()
                .map(venueSeatTypeMap::get)
                .filter(Objects::nonNull)
                .filter(VenueSeatType.VIP::equals)
                .count();

        final BigDecimal normalTotalPrice = performance.getNormalPrice().multiply(BigDecimal.valueOf(totalNormalSeatCount));
        final BigDecimal vipTotalPrice = performance.getVipPrice().multiply(BigDecimal.valueOf(totalVipSeatCount));

        return normalTotalPrice.add(vipTotalPrice);
    }

    private void validateEligibleForReservation(Performance performance, ReservationRequest reservationRequest) {
        final List<Long> requestedSeatIds = reservationRequest.getReservationSeatIds();
        final int requestedReserveSeatCount = requestedSeatIds.size();

        if (MAX_RESERVABLE_SEATS < requestedReserveSeatCount) {
            throw new ReservationException(RESERVATION_SEAT_EXCEED_LIMIT);
        }

        performance.checkReservable(requestedReserveSeatCount);

        if (!CollectionUtils.isEmpty(getReservationSeatBy(requestedSeatIds))) {
            throw new ReservationException(RESERVATION_SEAT_OCCUPIED);
        }
    }

}
