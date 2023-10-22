package com.example.ticketing.domain;

import com.example.ticketing.type.PaymentMethodType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
public class PaymentInfo {
    private PaymentMethodType paymentMethodType;
    private String cardNumber;
    private LocalDateTime cardExpiredAt;
    private String cardCVC;
}
