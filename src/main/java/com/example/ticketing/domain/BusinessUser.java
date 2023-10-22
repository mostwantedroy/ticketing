package com.example.ticketing.domain;

import com.example.ticketing.type.BusinessUserType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BusinessUser {
    private Long businessUserId;
    private String email;
    private String password;
    private String businessLicense;
    private BusinessUserType businessUserType;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    @Builder(builderMethodName = "builder")
    public BusinessUser(Long businessUserId, String email, String password, String businessLicense, BusinessUserType businessUserType,
                        LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.businessUserId = businessUserId;
        this.email = email;
        this.password = password;
        this.businessLicense = businessLicense;
        this.businessUserType = businessUserType;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    @Builder(builderMethodName = "create")
    public BusinessUser(String email, String password, String businessLicense, BusinessUserType businessUserType) {
        this.email = email;
        this.password = password;
        this.businessLicense = businessLicense;
        this.businessUserType = businessUserType;
    }
}
