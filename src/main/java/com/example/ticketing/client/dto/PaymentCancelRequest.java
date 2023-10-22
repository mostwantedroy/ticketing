package com.example.ticketing.client.dto;

import com.example.ticketing.domain.PaymentInfo;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PaymentCancelRequest {
    private Long userId;
    private BigDecimal requestAmount;
    private PaymentInfo paymentInfo;
}