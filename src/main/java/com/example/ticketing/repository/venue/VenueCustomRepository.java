package com.example.ticketing.repository.venue;

import com.example.ticketing.entity.VenueEntity;

import java.util.Optional;

public interface VenueCustomRepository {
    Optional<VenueEntity> findByVenueId(Long venueId);
}
