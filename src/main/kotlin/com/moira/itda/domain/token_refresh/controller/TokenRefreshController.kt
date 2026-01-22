package com.moira.itda.domain.token_refresh.controller

import com.moira.itda.domain.token_refresh.dto.response.TokenRefreshResponse
import com.moira.itda.domain.token_refresh.service.TokenRefreshService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TokenRefreshController(
    private val service: TokenRefreshService
) {
    /**
     * 토큰 재발급
     */
    @PostMapping("/api/token/refresh")
    fun refresh(
        httpReq: HttpServletRequest,
        httpRes: HttpServletResponse
    ): ResponseEntity<TokenRefreshResponse?> {
        val response = service.refresh(httpReq = httpReq, httpRes = httpRes)

        return ResponseEntity.ok(response)
    }
}