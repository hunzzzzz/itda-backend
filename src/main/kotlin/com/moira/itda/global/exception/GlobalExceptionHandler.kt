package com.moira.itda.global.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.ZonedDateTime

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ItdaException::class)
    fun handleItdaException(e: ItdaException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            errorCode = e.errorCode,
            message = e.message ?: ErrorCode.INTERNAL_SERVER_ERROR.message,
            time = ZonedDateTime.now()
        )

        return ResponseEntity.status(e.errorCode.httpStatus).body(errorResponse)
    }
}