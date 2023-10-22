package com.example.ticketing.service.businessuser.dto;

import com.example.ticketing.type.BusinessUserType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class BusinessUserRequest {
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "invalid password format")
    private String password;
    private String businessLicense;
    @NotNull
    private BusinessUserType businessUserType;
}
