package com.example.ticketing.service.user.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
}