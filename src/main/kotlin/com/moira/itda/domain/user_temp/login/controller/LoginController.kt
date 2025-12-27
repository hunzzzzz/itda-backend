package com.moira.itda.domain.user_temp.login.controller

import com.moira.itda.domain.user_temp.login.dto.request.LoginRequest
import com.moira.itda.domain.user_temp.login.dto.response.LoginResponse
import com.moira.itda.domain.user_temp.login.service.LoginService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 로그인 페이지
 */
@RestController
class LoginController(
    private val loginService: LoginService
) {
    /**
     * 로그인
     */
    @PostMapping("/api/login")
    fun login(
        @RequestBody request: LoginRequest,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    ): ResponseEntity<LoginResponse> {
        val response = loginService.login(
            request = request,
            httpServletRequest = httpServletRequest,
            httpServletResponse = httpServletResponse
        )

        return ResponseEntity.ok(response)
    }
}