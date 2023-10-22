package com.example.ticketing.service.reservation.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReservationSeatRequest {
    @NotNull
    private Long seatId;
}