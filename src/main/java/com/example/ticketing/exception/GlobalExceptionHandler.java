package com.example.ticketing.exception;

import com.example.ticketing.common.ResponseData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservationException.class)
    public ResponseData<?> handleReservationException(ReservationException exception) {
        return ResponseData.fail(exception);
    }
}
