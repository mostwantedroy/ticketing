package com.example.ticketing.service.venue.dto;

import com.example.ticketing.type.VenueType;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.List;

@Data
public class VenueRequest {
	@Size(min = 3, max = 128)
	private String name;
	@NotNull
	private VenueType venueType;
	@NotNull
	private LocalTime runningStartedAt;
	@NotNull
	private LocalTime runningEndedAt;
	@Valid
	@Size(min = 1)
	private List<VenueSeatRequest> venueSeats;
}
