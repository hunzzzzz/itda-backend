package com.moira.itda.global.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieHandler {
    private static final Long COOKIE_RTK_MAX_AGE = 60 * 60 * 24L; // 24시간

    public void putRtkInCookie(String rtk, HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", rtk)
                .maxAge(COOKIE_RTK_MAX_AGE)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void removeRtkInCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken")
                .maxAge(0)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
