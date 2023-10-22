package com.example.ticketing.common;

import com.example.ticketing.exception.ReservationException;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Data
public class ResponseData<T> {
    private Boolean success;
    private T result;
    private Integer status;
    private ErrorDetail error;

    @Data
    @Builder
    private static class ErrorDetail {
        private String reason;
        private String code;
    }

    public static <T> ResponseData<T> success(T result) {
        final ResponseData<T> responseData = new ResponseData<>();

        responseData.setSuccess(Boolean.TRUE);
        responseData.setResult(result);
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setError(null);

        return responseData;
    }

    public static <T> ResponseData<T> fail(ReservationException exception) {
        final ResponseData<T> responseData = new ResponseData<>();
        final ErrorCode errorCode = exception.getErrorCode();

        final ErrorDetail errorDetail = ErrorDetail.builder()
                .reason(Optional.ofNullable(errorCode)
                        .map(ErrorCode::getMessage)
                        .orElse(StringUtils.EMPTY))
                .code(Optional.ofNullable(errorCode)
                        .map(ErrorCode::getCode)
                        .orElse(null))
                .build();

        responseData.setSuccess(false);
        responseData.setResult(null);
        responseData.setStatus(Optional.ofNullable(errorCode)
                .map(ErrorCode::getHttpStatus)
                .map(HttpStatus::value)
                .orElse(null));
        responseData.setError(errorDetail);

        return responseData;
    }
}