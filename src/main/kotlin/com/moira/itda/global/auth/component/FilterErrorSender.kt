package com.moira.itda.global.auth.component

import com.moira.itda.global.exception.ErrorCode
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import kotlin.jvm.javaClass
import kotlin.text.trimIndent

@Component
class FilterErrorSender {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * 필터단 에러 처리
     */
    fun sendErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
        try {
            response.status = errorCode.httpStatus.value()
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.characterEncoding = "UTF-8"

            val errorResponseJson =
                """
                    {
                        "errorCode": "$errorCode",
                        "message": "${errorCode.message}",
                        "time": "${ZonedDateTime.now()}"
                    }
                """.trimIndent()

            response.writer.write(errorResponseJson)
        } catch (e: Exception) {
            log.error("[FilterErrorSender] 에러 발생! {}", e.message)
        }
    }
}
