package com.example.ticketing.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {
    private Long userId;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    @Builder(builderMethodName = "builder")
    public User(Long userId, String email, String password, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    @Builder(builderMethodName = "create")
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
