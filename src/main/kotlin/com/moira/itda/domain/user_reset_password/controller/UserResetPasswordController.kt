package com.moira.itda.domain.user_reset_password.controller

import com.moira.itda.domain.user_reset_password.dto.request.ResetPasswordRequest
import com.moira.itda.domain.user_reset_password.service.UserResetPasswordService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserResetPasswordController(
    private val service: UserResetPasswordService
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