package com.moira.itda.domain.user_temp.logout.service

import com.moira.itda.domain.user_temp.logout.mapper.LogoutMapper
import com.moira.itda.global.auth.component.CookieHandler
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LogoutService(
    private val cookieHandler: CookieHandler,
    private val logoutMapper: LogoutMapper
) {
    /**
     * 상단바 > 로그아웃
     */
    @Transactional
    fun logout(userId: String, httpServletResponse: HttpServletResponse) {
        // [1] RTK 제거 (DB)
        logoutMapper.updateRefreshTokenNull(userId = userId)

        // [2] RTK 제거 (쿠키)
        cookieHandler.removeRtkInCookie(response = httpServletResponse)
    }
}