package com.moira.itda.global.auth.filter;

import com.moira.itda.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class FilterErrorSender {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void sendErrorResponse(HttpServletResponse httpServletResponse, ErrorCode errorCode) {
        try {
            httpServletResponse.setStatus(errorCode.getHttpStatus().value());
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setCharacterEncoding("UTF-8");

            String errorResponseJson = """
                {"errorCode":"%s", "message":"%s", "time":"%s"}
                """.formatted(errorCode, errorCode.getMessage(), ZonedDateTime.now());
            httpServletResponse.getWriter().write(errorResponseJson);
        } catch (Exception e) {
            log.error("에러 발생! {}", e.getMessage());
        }
    }
}
