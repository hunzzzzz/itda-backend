package com.moira.itda.global.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ItdaException.class)
    public ErrorResponse handleItdaException(ItdaException e) {
        return ErrorResponse.builder()
                .errorCode(e.getErrorCode())
                .message(e.getMessage())
                .time(ZonedDateTime.now())
                .build();
    }
}
