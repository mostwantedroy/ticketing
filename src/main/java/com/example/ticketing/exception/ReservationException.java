package com.example.ticketing.exception;

import com.example.ticketing.common.ErrorCode;
import lombok.Getter;

@Getter
public class ReservationException extends RuntimeException {
    private ErrorCode errorCode;

    public ReservationException(String message) {
        super(message);
    }

    public ReservationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
