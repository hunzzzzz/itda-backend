package com.moira.itda.domain.user.controller

import com.moira.itda.domain.user.request.SignupRequest
import com.moira.itda.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 회원가입
 */
@RestController
class UserController(
    private val service: UserService
) {
    /**
     * 회원가입 > 닉네임 중복 확인
     */
    @GetMapping("/api/signup/check/nickname")
    fun checkNickname(@RequestParam nickname: String): ResponseEntity<Nothing?> {
        service.checkNickname(nickname = nickname)

        return ResponseEntity.ok(null)
    }

    /**
     * 회원가입 > 본인인증
     */
    @GetMapping("/api/signup/identify")
    fun checkEmail(@RequestParam email: String): ResponseEntity<Nothing?> {
        service.identify(email = email)

        return ResponseEntity.ok(null)
    }

    /**
     * 회원가입 > 본인인증 > 코드 확인
     */
    @GetMapping("/api/signup/identify/check/code")
    fun checkIdentifyCode(
        @RequestParam email: String,
        @RequestParam code: String
    ): ResponseEntity<Nothing?> {
        service.checkIdentifyCode(email = email, code = code)

        return ResponseEntity.ok(null)
    }

    /**
     * 회원가입
     */
    @PostMapping("/api/signup")
    fun signup(@RequestBody request: SignupRequest): ResponseEntity<Nothing?> {
        service.signup(request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }
}