package com.moira.itda.domain.user_temp.logout.controller

import com.moira.itda.domain.user_temp.logout.service.LogoutService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 상단바 > 로그아웃
 */
@RestController
class LogoutController(
    private val logoutService: LogoutService
) {
    /**
     * 상단바 > 로그아웃
     */
    @PostMapping("/api/logout")
    fun logout(
        @UserPrincipal userAuth: UserAuth,
        httpServletResponse: HttpServletResponse
    ): ResponseEntity<Nothing> {
        logoutService.logout(userId = userAuth.userId, httpServletResponse = httpServletResponse)

        return ResponseEntity.ok(null)
    }
}