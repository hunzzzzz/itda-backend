package com.moira.itda.global.exception;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record ErrorResponse(
        ErrorCode errorCode,
        String message,
        ZonedDateTime time
) {
}
