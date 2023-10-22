package com.example.ticketing.repository.reservation;

import com.example.ticketing.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long>, ReservationCustomRepository {
}
