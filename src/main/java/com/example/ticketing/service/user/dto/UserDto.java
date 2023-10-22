package com.example.ticketing.service.user.dto;

import com.example.ticketing.domain.User;
import com.example.ticketing.util.DateTimeUtils;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class UserDto {
	private Long userId;
	private String email;
	private String createdAt;
	private String lastModifiedAt;

	public static UserDto fromDomain(User user) {
		if (Objects.isNull(user)) {
			return null;
		}

		return UserDto.builder()
			.userId(user.getUserId())
			.email(user.getEmail())
			.createdAt(DateTimeUtils.getFrontDateTime(user.getCreatedAt()))
			.lastModifiedAt(DateTimeUtils.getFrontDateTime(user.getLastModifiedAt()))
			.build();
	}
}
