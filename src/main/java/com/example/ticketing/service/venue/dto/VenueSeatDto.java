package com.example.ticketing.service.venue.dto;

import com.example.ticketing.domain.VenueSeat;
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
public class VenueSeatDto {
	private Long seatId;
	private String seatNumber;
	private VenueSeatType seatType;

	public static VenueSeatDto fromEntity(VenueSeat venueSeat) {
		if (Objects.isNull(venueSeat)) {
			return null;
		}

		return VenueSeatDto.builder()
			.seatId(venueSeat.getSeatId())
			.seatNumber(venueSeat.getSeatNumber())
			.seatType(venueSeat.getSeatType())
			.build();
	}

	public static List<VenueSeatDto> fromEntity(List<VenueSeat> venueSeats) {
		if (CollectionUtils.isEmpty(venueSeats)) {
			return Collections.emptyList();
		}

		return venueSeats.stream()
			.map(VenueSeatDto::fromEntity)
			.collect(Collectors.toList());
	}
}
