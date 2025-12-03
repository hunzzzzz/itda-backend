package com.moira.itda.global.auth.filter;

import com.moira.itda.global.exception.ErrorCode;
import com.moira.itda.global.exception.ItdaException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final FilterErrorSender filterErrorSender;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("[ExceptionHandlerFilter] 에러 발생! {}", e.getMessage());

            // 일반 Java 예외는 내부 시스템 오류로 매핑하고, 커스텀 예외는 그대로 사용한다.
            ErrorCode errorCode = (e instanceof ItdaException) ? ((ItdaException) e).getErrorCode() : ErrorCode.INTERNAL_SYSTEM_ERROR;

            // ErrorCode 리턴
            filterErrorSender.sendErrorResponse(response, errorCode);
        }
    }
}
