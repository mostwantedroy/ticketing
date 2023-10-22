package com.example.ticketing.service.venue.dto;

import com.example.ticketing.domain.Venue;
import com.example.ticketing.type.VenueType;
import com.example.ticketing.util.DateTimeUtils;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@Builder
public class VenueDto {
    private Long venueId;
    private Long businessUserId;
    private String name;
    private VenueType venueType;
    private String runningStarted;
    private String runningEndedAt;
    private List<VenueSeatDto> venueSeats;
    private String createdAt;
    private Long createdBy;
    private String lastModifiedAt;
    private Long lastModifiedBy;

    public static VenueDto fromDomain(Venue venue) {
        if (Objects.isNull(venue)) {
            return null;
        }

        return VenueDto.builder()
                .venueId(venue.getVenueId())
                .businessUserId(venue.getBusinessUserId())
                .name(venue.getName())
                .venueType(venue.getVenueType())
                .runningStarted(DateTimeUtils.getFrontTime(venue.getRunningStartedAt()))
                .runningEndedAt(DateTimeUtils.getFrontTime(venue.getRunningEndedAt()))
                .venueSeats(VenueSeatDto.fromEntity(venue.getVenueSeats()))
                .createdAt(DateTimeUtils.getFrontDateTime(venue.getCreatedAt()))
                .createdBy(venue.getCreatedBy())
                .lastModifiedAt(DateTimeUtils.getFrontDateTime(venue.getLastModifiedAt()))
                .lastModifiedBy(venue.getLastModifiedBy())
                .build();
    }
}
