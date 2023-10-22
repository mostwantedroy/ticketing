package com.example.ticketing.service.performance.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PerformanceRequest {
    @Size(min = 2, max = 128)
    private String name;
    @Min(1)
    private Integer capacity;
    @NotNull
    private LocalDateTime startAt;
    @NotNull
    private LocalDateTime endAt;
    @NotNull
    private BigDecimal normalPrice;
    @NotNull
    private BigDecimal vipPrice;
}
