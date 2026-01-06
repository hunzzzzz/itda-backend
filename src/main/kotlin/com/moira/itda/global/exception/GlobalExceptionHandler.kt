package com.moira.itda.global.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException
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

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxUploadSizeException(e: MaxUploadSizeExceededException): ResponseEntity<ErrorResponse> {
        val errorCode = ErrorCode.MAX_UPLOAD_SIZE_EXCEEDED
        val errorResponse = ErrorResponse(
            errorCode = errorCode,
            message = errorCode.message,
            time = ZonedDateTime.now()
        )

        return ResponseEntity.status(errorCode.httpStatus).body(errorResponse)
    }
}