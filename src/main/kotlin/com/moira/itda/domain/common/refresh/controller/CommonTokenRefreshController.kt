package com.moira.itda.domain.common.refresh.controller

import com.moira.itda.domain.common.refresh.dto.response.NewTokenResponse
import com.moira.itda.domain.common.refresh.service.CommonTokenRefreshService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CommonTokenRefreshController(
    private val service: CommonTokenRefreshService
) {
    /**
     * 토큰 재발급
     */
    @PostMapping("/api/token/refresh")
    fun refresh(
        httpReq: HttpServletRequest,
        httpRes: HttpServletResponse
    ): ResponseEntity<NewTokenResponse?> {
        val response = service.refresh(httpReq = httpReq, httpRes = httpRes)

        return ResponseEntity.ok(response)
    }
}