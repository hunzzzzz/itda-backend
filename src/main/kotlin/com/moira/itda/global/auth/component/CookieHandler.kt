package com.moira.itda.global.auth.component

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component

@Component
class CookieHandler {
    companion object {
        // 쿠키명
        private const val COOKIE_RTK_NAME = "refreshToken"
        private const val COOKIE_GACHA_ID_NAME = "gachaId"

        // 쿠키 만료 시간
        private const val COOKIE_RTK_MAX_AGE = 60 * 60 * 24L // 24시간
        private const val COOKIE_GACHA_ID_MAX_AGE = 60 * 60 * 2L // 2시간
    }

    /**
     * 쿠키에 RefreshToken 추가
     */
    fun putRtkInCookie(rtk: String, response: HttpServletResponse) {
        val cookie = ResponseCookie.from(COOKIE_RTK_NAME, rtk)
            .maxAge(COOKIE_RTK_MAX_AGE)
            .path("/")
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
    }

    /**
     * 쿠키에서 RefreshToken 추출
     */
    fun getRtkFromCookie(request: HttpServletRequest): String? {
        return request.cookies?.find { cookie -> cookie.name == COOKIE_RTK_NAME }?.value
    }

    /**
     * 쿠키에 RefreshToken 제거
     */
    fun removeRtkInCookie(response: HttpServletResponse) {
        val cookie = ResponseCookie.from(COOKIE_RTK_NAME)
            .maxAge(0)
            .path("/")
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
    }

    /**
     * 쿠키에 GachaId 존재 여부 확인
     */
    fun checkGachaIdInCookie(gachaId: String, request: HttpServletRequest): Boolean {
        return request.cookies?.any { cookie -> cookie.name == "${COOKIE_GACHA_ID_NAME}_${gachaId}" } ?: false
    }

    /**
     * 쿠키에 GachaId 추가
     */
    fun putGachaIdInCookie(gachaId: String, response: HttpServletResponse) {
        val cookie = ResponseCookie.from("${COOKIE_GACHA_ID_NAME}_${gachaId}", gachaId)
            .maxAge(COOKIE_GACHA_ID_MAX_AGE)
            .path("/")
            .httpOnly(true)
            .secure(true)
            .sameSite("None")
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
    }
}
