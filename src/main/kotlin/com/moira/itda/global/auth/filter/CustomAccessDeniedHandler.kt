package com.moira.itda.global.auth.filter

import com.moira.itda.global.auth.component.FilterErrorSender
import com.moira.itda.global.auth.component.JwtProvider
import com.moira.itda.global.exception.ErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.http.HttpHeaders
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler(
    private val filterErrorSender: FilterErrorSender,
    private val jwtProvider: JwtProvider
) : AccessDeniedHandler {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        // [1] 유저 정보 추출
        request.getHeader(HttpHeaders.AUTHORIZATION).let { headerVal ->
            val atk = jwtProvider.substringToken(headerVal)
            jwtProvider.validateToken(atk).onSuccess {
                val userId = it?.subject

                if (userId != null) {
                    log.warn(
                        "[경고] 권한이 없는 유저 {}가 특정 리소스에 접근을 시도하고 있습니다. {} {}",
                        userId,
                        request.method,
                        request.requestURI
                    )
                }
            }
        }

        // [2] 에러 처리
        filterErrorSender.sendErrorResponse(response = response, errorCode = ErrorCode.ACCESS_DENIED)
    }
}