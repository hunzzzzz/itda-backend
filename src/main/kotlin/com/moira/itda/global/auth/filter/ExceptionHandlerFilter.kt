package com.moira.itda.global.auth.filter

import com.moira.itda.global.auth.component.FilterErrorSender
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class ExceptionHandlerFilter(
    private val filterErrorSender: FilterErrorSender
) : OncePerRequestFilter() {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        kotlin.runCatching { filterChain.doFilter(request, response) }
            .onFailure {
                log.error("[ExceptionHandlerFilter] 에러 발생! {}", it.message)

                // 일반 Java 예외는 내부 시스템 오류로 매핑
                // 커스텀 예외(ItdaException)은 그대로 사용
                val errorCode = if (it is ItdaException) it.errorCode else ErrorCode.INTERNAL_SERVER_ERROR

                filterErrorSender.sendErrorResponse(response, errorCode)
            }
    }
}