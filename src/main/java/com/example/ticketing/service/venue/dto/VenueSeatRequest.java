package com.example.ticketing.service.venue.dto;

import com.example.ticketing.type.VenueSeatType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class VenueSeatRequest {
	@Size(min = 1, max = 26)
	private String seatNumber;
	@NotNull
	private VenueSeatType seatType;
}
