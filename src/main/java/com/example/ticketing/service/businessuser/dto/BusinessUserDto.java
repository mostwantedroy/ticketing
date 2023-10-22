package com.example.ticketing.service.businessuser.dto;

import com.example.ticketing.domain.BusinessUser;
import com.example.ticketing.type.BusinessUserType;
import com.example.ticketing.util.DateTimeUtils;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class BusinessUserDto {
    private Long businessUserId;
    private String email;
    private String businessLicense;
    private BusinessUserType businessUserType;
    private String createdAt;
    private String lastModifiedAt;

    public static BusinessUserDto fromDomain(BusinessUser businessUser) {
        if (Objects.isNull(businessUser)) {
            return null;
        }

        return BusinessUserDto.builder()
                .businessUserId(businessUser.getBusinessUserId())
                .email(businessUser.getEmail())
                .businessLicense(businessUser.getBusinessLicense())
                .businessUserType(businessUser.getBusinessUserType())
                .createdAt(DateTimeUtils.getFrontDateTime(businessUser.getCreatedAt()))
                .lastModifiedAt(DateTimeUtils.getFrontDateTime(businessUser.getLastModifiedAt()))
                .build();
    }
}
