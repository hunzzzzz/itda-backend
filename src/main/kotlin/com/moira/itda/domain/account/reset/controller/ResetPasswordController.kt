package com.moira.itda.domain.account.reset.controller

import com.moira.itda.domain.account.reset.dto.request.ResetPasswordRequest
import com.moira.itda.domain.account.reset.service.ResetPasswordService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ResetPasswordController(
    private val service: ResetPasswordService
) {
    /**
     * 비밀번호 초기화
     */
    @PutMapping("/api/reset/password")
    fun resetPassword(
        @RequestBody request: ResetPasswordRequest
    ): ResponseEntity<Nothing?> {
        service.resetPassword(request = request)

        return ResponseEntity.ok(null)
    }
}