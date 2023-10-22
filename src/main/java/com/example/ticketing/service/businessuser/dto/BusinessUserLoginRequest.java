package com.example.ticketing.service.businessuser.dto;

import lombok.Data;

@Data
public class BusinessUserLoginRequest {
    private String email;
    private String password;
}
