package com.example.ticketing.entity;


import com.example.ticketing.domain.Reservation;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "reservation")
@EqualsAndHashCode(of = "reservationId")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column
    private Long userId;

    @Column
    private Long performanceId;

    @Embedded
    private PaymentInfoVo paymentInfoVo;

    @Column
    private BigDecimal totalPrice;

    @Column
    private BigDecimal normalPrice;

    @Column
    private BigDecimal vipPrice;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReservationSeatEntity> reservationSeats;

    @Builder
    public ReservationEntity(Long userId, Long performanceId, PaymentInfoVo paymentInfoVo, BigDecimal totalPrice,
                             BigDecimal normalPrice, BigDecimal vipPrice, List<ReservationSeatEntity> reservationSeats) {

        this.userId = userId;
        this.performanceId = performanceId;
        this.paymentInfoVo = paymentInfoVo;
        this.totalPrice = totalPrice;
        this.normalPrice = normalPrice;
        this.vipPrice = vipPrice;
        this.reservationSeats = reservationSeats;
        this.reservationSeats.forEach(reservationSeat -> reservationSeat.apply(this));
    }

    public Reservation toDomain() {
        return Reservation.builder()
                .reservationId(reservationId)
                .userId(userId)
                .performanceId(performanceId)
                .paymentInfo(paymentInfoVo.toDomain())
                .totalPrice(totalPrice)
                .normalPrice(normalPrice)
                .vipPrice(vipPrice)
                .reservationSeats(reservationSeats.stream()
                        .map(ReservationSeatEntity::toDomain)
                        .collect(Collectors.toList()))
                .build();
    }

    public static ReservationEntity fromDomain(Reservation reservation) {
        return ReservationEntity.builder()
                .userId(reservation.getUserId())
                .performanceId(reservation.getPerformanceId())
                .paymentInfoVo(PaymentInfoVo.fromDomain(reservation.getPaymentInfo()))
                .totalPrice(reservation.getTotalPrice())
                .normalPrice(reservation.getNormalPrice())
                .vipPrice(reservation.getVipPrice())
                .reservationSeats(ReservationSeatEntity.fromDomain(reservation.getReservationSeats()))
                .build();
    }
}
