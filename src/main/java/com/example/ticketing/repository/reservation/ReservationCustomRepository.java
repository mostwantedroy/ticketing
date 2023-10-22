package com.example.ticketing.repository.reservation;

import com.example.ticketing.entity.ReservationEntity;
import com.example.ticketing.entity.ReservationSeatEntity;

import java.util.List;
import java.util.Optional;

public interface ReservationCustomRepository {
	List<ReservationSeatEntity> findAllReservationSeatsByPerformanceId(Long performanceId);

	List<ReservationSeatEntity> findByVenueSeatIds(List<Long> venueSeatIds);

	Optional<ReservationEntity> findByReservationId(Long reservationId);
}
