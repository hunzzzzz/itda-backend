package com.moira.itda.domain.user.signup.controller

import com.moira.itda.domain.user.signup.dto.request.SignupRequest
import com.moira.itda.domain.user.signup.service.SignupService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 회원가입 페이지
 */
@RestController
class SignupController(
    private val signupService: SignupService
) {
    /**
     * 회원가입 > 닉네임 중복 확인
     */
    @GetMapping("/api/signup/check/nickname")
    fun checkNickname(@RequestParam nickname: String): ResponseEntity<Nothing> {
        signupService.checkNickname(nickname = nickname)

        return ResponseEntity.ok(null)
    }

    /**
     * TODO: 회원가입 > 이메일 본인인증
     */
    @GetMapping("/api/signup/identify")
    fun checkEmail(@RequestParam email: String): ResponseEntity<Nothing> {
        signupService.identify(email = email)

        return ResponseEntity.ok(null)
    }

    /**
     * 회원가입
     */
    @PostMapping("/api/signup")
    fun signup(@RequestBody request: SignupRequest): ResponseEntity<Nothing> {
        signupService.signup(request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }
}