package com.example.ticketing.controller;

import com.example.ticketing.common.ResponseData;
import com.example.ticketing.domain.Performance;
import com.example.ticketing.domain.Reservation;
import com.example.ticketing.domain.User;
import com.example.ticketing.service.performance.PerformanceService;
import com.example.ticketing.service.performance.dto.PerformanceDto;
import com.example.ticketing.service.reservation.ReservationService;
import com.example.ticketing.service.reservation.dto.ReservationDto;
import com.example.ticketing.service.reservation.dto.ReservationRequest;
import com.example.ticketing.service.reservation.dto.ReservationSeatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
    private final ReservationService reservationService;
    private final PerformanceService performanceService;

    @PostMapping("/{performanceId}/reservations")
    public ResponseData<ReservationDto> reserve(User user, @PathVariable Long performanceId, @RequestBody @Valid ReservationRequest reservationRequest) {
        Reservation reservation = reservationService.reserve(user.getUserId(), performanceId, reservationRequest);

        return ResponseData.success(ReservationDto.fromDomain(reservation));
    }

    @GetMapping("/seats")
    public ResponseData<List<ReservationSeatDto>> getReservedSeats() {
        //TODO

        return null;
    }

    @GetMapping("/reservable")
    public ResponseData<Page<PerformanceDto>> getReservablePerformances(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) {

        Page<Performance> pagedPerformances = performanceService.findReservablePerformancesOn(PageRequest.of(page, size));

        return ResponseData.success(getPagedPerformanceDtos(pagedPerformances, PageRequest.of(page, size)));
    }

    @GetMapping("/best")
    public ResponseData<Page<PerformanceDto>> getBestPerformances(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) {

        Page<Performance> pagedPerformances = performanceService.findBestPerformances(PageRequest.of(page, size));

        return ResponseData.success(getPagedPerformanceDtos(pagedPerformances, PageRequest.of(page, size)));
    }

    @GetMapping("/cheapest")
    public ResponseData<Page<PerformanceDto>> getCheapestPerformances(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) {

        Page<Performance> pagedPerformances = performanceService.findCheapestPerformances(PageRequest.of(page, size));

        return ResponseData.success(getPagedPerformanceDtos(pagedPerformances, PageRequest.of(page, size)));
    }

    private static Page<PerformanceDto> getPagedPerformanceDtos(Page<Performance> pagedPerformances, PageRequest pageRequest) {
        if (Objects.isNull(pagedPerformances) || CollectionUtils.isEmpty(pagedPerformances.getContent())) {
            return Page.empty();
        }

        return new PageImpl<>(
                pagedPerformances.getContent()
                        .stream()
                        .map(PerformanceDto::fromDomain)
                        .collect(Collectors.toList()),
                pageRequest,
                pagedPerformances.getTotalElements()
        );
    }

}
