package com.moira.itda.domain.user_signup.controller

import com.moira.itda.domain.user_signup.dto.request.SignupEmailCheckRequest
import com.moira.itda.domain.user_signup.dto.request.SignupRequest
import com.moira.itda.domain.user_signup.service.UserSignupService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 회원가입
 */
@RestController
class UserSignupController(
    private val service: UserSignupService
) {
    /**
     * 회원가입 > 이메일 중복 확인
     */
    @PostMapping("/api/signup/email/check")
    fun checkEmailDuplication(
        @RequestBody request: SignupEmailCheckRequest
    ): ResponseEntity<Nothing?> {
        service.checkEmailDuplication(email = request.email)

        return ResponseEntity.ok(null)
    }

    /**
     * 회원가입
     */
    @PostMapping("/api/signup")
    fun signup(
        @RequestBody request: SignupRequest
    ): ResponseEntity<Nothing?> {
        service.signup(request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }
}