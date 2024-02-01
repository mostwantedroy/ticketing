package com.example.ticketing.repository.venue;

import com.example.ticketing.entity.VenueEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.ticketing.entity.QVenueEntity.venueEntity;

@Repository
@RequiredArgsConstructor
public class VenueCustomRepositoryImpl implements VenueCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<VenueEntity> findByVenueId(Long venueId) {
        VenueEntity venue = queryFactory.selectFrom(venueEntity)
                .where(venueEntity.venueId.eq(venueId))
                .leftJoin(venueEntity.venueSeats)
                .fetchJoin()
                .fetchOne();

        return Optional.ofNullable(venue);
    }
}
