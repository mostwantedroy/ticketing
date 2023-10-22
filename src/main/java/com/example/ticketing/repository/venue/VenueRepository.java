package com.example.ticketing.repository.venue;

import com.example.ticketing.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<VenueEntity, Long>, VenueCustomRepository {
}
