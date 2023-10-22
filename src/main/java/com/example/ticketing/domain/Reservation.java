package com.example.ticketing.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Reservation {
    private Long reservationId;
    private Long userId;
    private Long performanceId;
    private PaymentInfo paymentInfo;
    private BigDecimal totalPrice;
    private BigDecimal normalPrice;
    private BigDecimal vipPrice;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private List<ReservationSeat> reservationSeats;

    @Builder(builderMethodName = "builder")
    public Reservation(Long reservationId, Long userId, Long performanceId, PaymentInfo paymentInfo, BigDecimal totalPrice, BigDecimal normalPrice,
                       BigDecimal vipPrice, LocalDateTime createdAt, LocalDateTime lastModifiedAt, List<ReservationSeat> reservationSeats) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.performanceId = performanceId;
        this.paymentInfo = paymentInfo;
        this.totalPrice = totalPrice;
        this.normalPrice = normalPrice;
        this.vipPrice = vipPrice;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.reservationSeats = reservationSeats;
    }

    @Builder(builderMethodName = "create")
    public Reservation(Long userId, Long performanceId, PaymentInfo paymentInfo, BigDecimal totalPrice, BigDecimal normalPrice, BigDecimal vipPrice, List<ReservationSeat> reservationSeats) {
        this.userId = userId;
        this.performanceId = performanceId;
        this.paymentInfo = paymentInfo;
        this.totalPrice = totalPrice;
        this.normalPrice = normalPrice;
        this.vipPrice = vipPrice;
        this.reservationSeats = reservationSeats;
    }
}
