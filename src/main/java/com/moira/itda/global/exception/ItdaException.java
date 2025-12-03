package com.moira.itda.global.exception;

import lombok.Getter;

@Getter
public class ItdaException extends RuntimeException {
    private final ErrorCode errorCode;

    public ItdaException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
