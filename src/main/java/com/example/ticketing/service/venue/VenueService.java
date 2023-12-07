package com.example.ticketing.service.venue;

import com.example.ticketing.common.ErrorCode;
import com.example.ticketing.domain.BusinessUser;
import com.example.ticketing.domain.Venue;
import com.example.ticketing.domain.VenueSeat;
import com.example.ticketing.entity.VenueEntity;
import com.example.ticketing.entity.BusinessUserEntity;
import com.example.ticketing.exception.ReservationException;
import com.example.ticketing.repository.businessuser.BusinessUserRepository;
import com.example.ticketing.repository.venue.VenueRepository;
import com.example.ticketing.service.venue.dto.VenueRequest;
import com.example.ticketing.type.BusinessUserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VenueService {
    private final BusinessUserRepository businessUserRepository;
    private final VenueRepository venueRepository;

    @Transactional
    public Venue registerVenue(Long businessUserId, VenueRequest venueRequest) {
        if (Objects.isNull(venueRequest)) {
            throw new ReservationException("empty venue request");
        }

        BusinessUser businessUser = businessUserRepository.findById(businessUserId)
                .map(BusinessUserEntity::toDomain)
                .orElseThrow(() -> new ReservationException(ErrorCode.NOT_FOUND_ENTITY));

        if (BusinessUserType.VENUE_ADMIN != businessUser.getBusinessUserType()) {
            throw new ReservationException(ErrorCode.UNAUTHORIZED);
        }

        Venue venue = createVenue(businessUserId, venueRequest);

        return venueRepository.save(VenueEntity.fromDomain(venue)).toDomain();
    }

    private Venue createVenue(Long businessUserId, VenueRequest venueRequest) {
        return Venue.create()
                .businessUserId(businessUserId)
                .name(venueRequest.getName())
                .venueType(venueRequest.getVenueType())
                .runningStartedAt(venueRequest.getRunningStartedAt())
                .runningEndedAt(venueRequest.getRunningEndedAt())
                .venueSeats(venueRequest.getVenueSeats()
                        .stream()
                        .map(venueSeatRequest -> VenueSeat.create()
                                .seatNumber(venueSeatRequest.getSeatNumber())
                                .seatType(venueSeatRequest.getSeatType())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }


    @Transactional(readOnly = true)
    public Venue getVenue(Long venueId) {
        return venueRepository.findByVenueId(venueId)
                .map(VenueEntity::toDomain)
                .orElseThrow(() -> new ReservationException(ErrorCode.NOT_FOUND_ENTITY));
    }

}
