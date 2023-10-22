package com.example.ticketing.client.dto;

import com.example.ticketing.domain.PaymentInfo;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PaymentCompletedDto {
    private Long userId;
    private BigDecimal requestAmount;
    private PaymentInfo paymentInfo;

    @Builder
    public PaymentCompletedDto(Long userId, BigDecimal requestAmount, PaymentInfo paymentInfo) {
        this.userId = userId;
        this.requestAmount = requestAmount;
        this.paymentInfo = paymentInfo;
    }
}
