package com.example.ticketing.client.dto;

import com.example.ticketing.domain.PaymentInfo;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentCompleteRequest {
    private Long userId;
    private BigDecimal requestAmount;
    private PaymentInfo paymentInfo;
}
