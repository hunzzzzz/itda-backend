package com.moira.itda.global.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException
import java.time.ZonedDateTime

@RestControllerAdvice
class GlobalExceptionHandler {
    /**
     * ItdaException (커스텀 에러)
     */
    @ExceptionHandler(ItdaException::class)
    fun handleItdaException(e: ItdaException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            errorCode = e.errorCode,
            message = e.message ?: ErrorCode.INTERNAL_SERVER_ERROR.message,
            time = ZonedDateTime.now()
        )

        return ResponseEntity.status(e.errorCode.httpStatus).body(errorResponse)
    }

    /**
     * MaxUploadSizeExceededException (이미지 용량 초과 에러)
     */
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