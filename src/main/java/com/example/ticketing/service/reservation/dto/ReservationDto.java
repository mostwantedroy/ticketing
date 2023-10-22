package com.example.ticketing.service.reservation.dto;

import com.example.ticketing.domain.PaymentInfo;
import com.example.ticketing.domain.Reservation;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Builder
public class ReservationDto {
    private Long reservationId;
    private Long userId;
    private Long performanceId;
    private PaymentInfo paymentInfo;
    private BigDecimal totalPrice;
    private BigDecimal normalPrice;
    private BigDecimal vipPrice;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private List<ReservationSeatDto> reservationSeats;

    public static ReservationDto fromDomain(Reservation reservation) {
        if (Objects.isNull(reservation)) {
            return null;
        }

        return ReservationDto.builder()
                .reservationId(reservation.getReservationId())
                .userId(reservation.getUserId())
                .performanceId(reservation.getPerformanceId())
                .paymentInfo(reservation.getPaymentInfo())
                .totalPrice(reservation.getTotalPrice())
                .normalPrice(reservation.getNormalPrice())
                .vipPrice(reservation.getVipPrice())
                .createdAt(reservation.getCreatedAt())
                .lastModifiedAt(reservation.getLastModifiedAt())
                .reservationSeats(ReservationSeatDto.fromDomain(reservation.getReservationSeats()))
                .build();
    }
}
