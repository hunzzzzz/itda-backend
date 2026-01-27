package com.moira.itda.domain.account.logout.controller

import com.moira.itda.domain.account.logout.service.LogoutService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LogoutController(
    private val service: LogoutService
) {
    /**
     * 로그아웃
     */
    @PostMapping("/api/logout")
    fun logout(
        @UserPrincipal userAuth: UserAuth,
        httpRes: HttpServletResponse
    ): ResponseEntity<Nothing> {
        service.logout(userId = userAuth.userId, httpRes = httpRes)

        return ResponseEntity.ok(null)
    }
}