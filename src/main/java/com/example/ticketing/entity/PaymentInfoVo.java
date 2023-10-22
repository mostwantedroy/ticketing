package com.example.ticketing.entity;

import com.example.ticketing.domain.PaymentInfo;
import com.example.ticketing.type.PaymentMethodType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentInfoVo {
    @Enumerated(EnumType.STRING)
    @Column
    private PaymentMethodType paymentMethodType;

    @Column
    private String cardNumber;

    @Column
    private LocalDateTime cardExpiredAt;

    @Column
    private String cardCVC;

    @Builder
    public PaymentInfoVo(PaymentMethodType paymentMethodType, String cardNumber, LocalDateTime cardExpiredAt, String cardCVC) {
        this.paymentMethodType = paymentMethodType;
        this.cardNumber = cardNumber;
        this.cardExpiredAt = cardExpiredAt;
        this.cardCVC = cardCVC;
    }

    public PaymentInfo toDomain(){
        return PaymentInfo.builder()
                .paymentMethodType(paymentMethodType)
                .cardNumber(cardNumber)
                .cardExpiredAt(cardExpiredAt)
                .cardCVC(cardCVC)
                .build();
    }

    public static PaymentInfoVo fromDomain(PaymentInfo paymentInfo){
        if(Objects.isNull(paymentInfo)){
            return null;
        }

        return PaymentInfoVo.builder()
                .paymentMethodType(paymentInfo.getPaymentMethodType())
                .cardNumber(paymentInfo.getCardNumber())
                .cardExpiredAt(paymentInfo.getCardExpiredAt())
                .cardCVC(paymentInfo.getCardCVC())
                .build();
    }
}
