package com.example.ticketing.repository.reservation;

import com.example.ticketing.entity.ReservationEntity;
import com.example.ticketing.entity.ReservationSeatEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.ticketing.entity.QReservationEntity.reservationEntity;
import static com.example.ticketing.entity.QReservationSeatEntity.reservationSeatEntity;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReservationSeatEntity> findAllReservationSeatsByPerformanceId(Long performanceId) {
        return queryFactory.selectFrom(reservationSeatEntity)
                .join(reservationSeatEntity.reservation, reservationEntity)
                .where(reservationEntity.performanceId.eq(performanceId))
                .fetch();
    }

    @Override
    public List<ReservationSeatEntity> findByVenueSeatIds(List<Long> venueSeatIds) {
        if (CollectionUtils.isEmpty(venueSeatIds)) {
            return Collections.emptyList();
        }

        return queryFactory.selectFrom(reservationSeatEntity)
                .where(reservationSeatEntity.venueSeatId.in(venueSeatIds))
                .fetch();
    }

    @Override
    public Optional<ReservationEntity> findByReservationId(Long reservationId) {
        ReservationEntity reservation = queryFactory.selectFrom(reservationEntity)
                .where(reservationEntity.reservationId.eq(reservationId))
                .leftJoin(reservationEntity.reservationSeats)
                .fetchJoin()
                .fetchOne();

        return Optional.ofNullable(reservation);
    }
}
