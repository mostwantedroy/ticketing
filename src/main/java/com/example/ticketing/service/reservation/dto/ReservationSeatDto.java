package com.example.ticketing.service.reservation.dto;

import com.example.ticketing.domain.ReservationSeat;
import com.example.ticketing.type.VenueSeatType;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
public class ReservationSeatDto {
	private Long reservationSeatId;
	private Long venueSeatId;
	private VenueSeatType seatType;

	public static List<ReservationSeatDto> fromDomain(List<ReservationSeat> reservationSeats) {
		if (CollectionUtils.isEmpty(reservationSeats)) {
			return Collections.emptyList();
		}

		return reservationSeats.stream()
			.map(ReservationSeatDto::fromDomain)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	public static ReservationSeatDto fromDomain(ReservationSeat reservationSeat) {
		if (Objects.isNull(reservationSeat)) {
			return null;
		}

		return ReservationSeatDto.builder()
			.reservationSeatId(reservationSeat.getReservationSeatId())
			.venueSeatId(reservationSeat.getVenueSeatId())
			.seatType(reservationSeat.getSeatType())
			.build();
	}
}
