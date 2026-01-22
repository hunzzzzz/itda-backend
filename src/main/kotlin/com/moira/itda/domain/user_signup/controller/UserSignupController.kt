package com.moira.itda.domain.user_signup.controller

import com.moira.itda.domain.user_signup.dto.request.SignupEmailCheckRequest
import com.moira.itda.domain.user_signup.dto.request.SignupIdentifyCheckRequest
import com.moira.itda.domain.user_signup.dto.request.SignupIdentifyRequest
import com.moira.itda.domain.user_signup.dto.request.SignupRequest
import com.moira.itda.domain.user_signup.dto.response.SignupIdentifyInfoResponse
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
     * 회원가입 > 휴대폰 본인인증
     */
    @PostMapping("/api/signup/phone/identify")
    fun identify(
        @RequestBody request: SignupIdentifyRequest
    ): ResponseEntity<Nothing?> {
        service.identify(request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 회원가입 > 휴대폰 본인인증 > 인증코드 확인
     */
    // TODO: 추후 본인인증 절차가 PASS 인증으로 변경되면 해당 인증코드 확인 로직은 사용하지 않는다.
    @PostMapping("/api/signup/phone/identify/check")
    fun checkIdentifyCode(
        @RequestBody request: SignupIdentifyCheckRequest
    ): ResponseEntity<SignupIdentifyInfoResponse> {
        val response = service.checkIdentifyCode(request = request)

        return ResponseEntity.ok(response)
    }

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