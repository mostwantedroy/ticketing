package com.example.ticketing.controller;

import com.example.ticketing.common.ResponseData;
import com.example.ticketing.domain.BusinessUser;
import com.example.ticketing.domain.Performance;
import com.example.ticketing.domain.Venue;
import com.example.ticketing.service.performance.PerformanceService;
import com.example.ticketing.service.performance.dto.PerformanceDto;
import com.example.ticketing.service.performance.dto.PerformanceRequest;
import com.example.ticketing.service.venue.VenueService;
import com.example.ticketing.service.venue.dto.VenueDto;
import com.example.ticketing.service.venue.dto.VenueRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/venue")
@RequiredArgsConstructor
public class VenueController {
    private final VenueService venueService;
    private final PerformanceService performanceService;

    @GetMapping("/{venueId}")
    public ResponseData<VenueDto> getVenue(@PathVariable("venueId") Long venueId) {
        final Venue venue = venueService.getVenue(venueId);

        return ResponseData.success(VenueDto.fromDomain(venue));
    }

    @PostMapping
    public ResponseData<VenueDto> registerVenue(BusinessUser businessUser, @RequestBody @Valid VenueRequest venueRequest) {
        final Venue venue = venueService.registerVenue(businessUser.getBusinessUserId(), venueRequest);

        return ResponseData.success(VenueDto.fromDomain(venue));
    }

    @PostMapping("/{venueId}/performances")
    public ResponseData<PerformanceDto> registerPerformance(BusinessUser businessUser, @PathVariable Long venueId, @RequestBody @Valid PerformanceRequest performanceRequest) {
        final Performance performance = performanceService.registerPerformance(venueId, businessUser.getBusinessUserId(), performanceRequest);

        return ResponseData.success(PerformanceDto.fromDomain(performance));
    }


}
