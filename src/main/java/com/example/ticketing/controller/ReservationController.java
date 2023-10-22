package com.example.ticketing.controller;

import com.example.ticketing.common.ResponseData;
import com.example.ticketing.domain.Reservation;
import com.example.ticketing.service.reservation.ReservationService;
import com.example.ticketing.service.reservation.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/{reservationId}")
    public ResponseData<ReservationDto> getReservation(@PathVariable Long reservationId) {
        Reservation reservation = reservationService.getReservation(reservationId);

        return ResponseData.success(ReservationDto.fromDomain(reservation));
    }
}
