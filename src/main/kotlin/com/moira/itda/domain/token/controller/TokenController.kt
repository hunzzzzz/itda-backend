package com.moira.itda.domain.token.controller

import com.moira.itda.domain.token.dto.response.TokenRefreshResponse
import com.moira.itda.domain.token.service.TokenService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 토큰 재발급
 */
@RestController
class TokenController(
    private val tokenService: TokenService
) {
    /**
     * 토큰 재발급
     */
    @PostMapping("/api/token/refresh")
    fun refresh(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    ): ResponseEntity<TokenRefreshResponse> {
        val response = tokenService.refresh(
            httpServletRequest = httpServletRequest,
            httpServletResponse = httpServletResponse
        )

        return ResponseEntity.ok(response)
    }
}