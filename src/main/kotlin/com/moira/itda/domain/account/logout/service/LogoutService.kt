package com.moira.itda.domain.account.logout.service

import com.moira.itda.domain.account.logout.mapper.LogoutMapper
import com.moira.itda.global.auth.component.CookieHandler
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LogoutService(
    private val cookieHandler: CookieHandler,
    private val mapper: LogoutMapper
) {
    /**
     * 로그아웃
     */
    @Transactional
    fun logout(userId: String, httpRes: HttpServletResponse) {
        // [1] RefreshToken 제거 (DB)
        mapper.updateRefreshTokenNull(userId = userId)

        // [2] RefreshToken 제거 (쿠키)
        cookieHandler.removeRtkInCookie(response = httpRes)
    }
}