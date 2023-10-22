package com.example.ticketing.client;

import com.example.ticketing.client.dto.PaymentCancelRequest;
import com.example.ticketing.client.dto.PaymentCompleteRequest;
import com.example.ticketing.client.dto.PaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayApiClient {
    public PaymentResponse completePayment(PaymentCompleteRequest paymentCompleteRequest) {
        try {
            Thread.sleep(300L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new PaymentResponse(true);
    }

    public PaymentResponse cancelPayment(PaymentCancelRequest paymentCancelRequest)  {
        try {
            Thread.sleep(300L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new PaymentResponse(true);
    }
}
