package com.moira.itda.domain.account.login.controller

import com.moira.itda.domain.account.login.dto.request.LoginRequest
import com.moira.itda.domain.account.login.dto.response.TokenResponse
import com.moira.itda.domain.account.login.service.LoginService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
    private val service: LoginService
) {
    /**
     * 로그인
     */
    @PostMapping("/api/login")
    fun login(
        @RequestBody request: LoginRequest,
        httpReq: HttpServletRequest,
        httpRes: HttpServletResponse
    ): ResponseEntity<TokenResponse> {
        val response = service.login(request = request, httpReq = httpReq, httpRes = httpRes)

        return ResponseEntity.ok(response)
    }
}