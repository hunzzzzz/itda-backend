package com.moira.itda.domain.login.controller

import com.moira.itda.domain.login.dto.request.LoginRequest
import com.moira.itda.domain.login.dto.response.LoginResponse
import com.moira.itda.domain.login.service.LoginService
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
    ): ResponseEntity<LoginResponse> {
        val response = service.login(request = request, httpReq = httpReq, httpRes = httpRes)

        return ResponseEntity.ok(response)
    }
}