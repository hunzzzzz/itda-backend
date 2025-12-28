package com.moira.itda.domain.user.controller

import com.moira.itda.domain.user.dto.request.LoginRequest
import com.moira.itda.domain.user.dto.request.SignupRequest
import com.moira.itda.domain.user.dto.response.LoginResponse
import com.moira.itda.domain.user.dto.response.MyPageResponse
import com.moira.itda.domain.user.dto.response.TokenRefreshResponse
import com.moira.itda.domain.user.service.UserService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 회원가입
 * 로그인
 * 로그아웃
 * 토큰 재발급
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

    /**
     * 로그아웃
     */
    @PostMapping("/api/logout")
    fun logout(
        @UserPrincipal userAuth: UserAuth,
        httpRes: HttpServletResponse
    ): ResponseEntity<Nothing> {
        service.logout(userId = userAuth.userId, httpRes = httpRes)

        return ResponseEntity.ok(null)
    }

    /**
     * 토큰 재발급
     */
    @PostMapping("/api/token/refresh")
    fun refresh(
        httpReq: HttpServletRequest,
        httpRes: HttpServletResponse
    ): ResponseEntity<TokenRefreshResponse> {
        val response = service.refresh(httpReq = httpReq, httpRes = httpRes)

        return ResponseEntity.ok(response)
    }

    /**
     * 마이페이지 > 내 프로필 조회
     */
    @GetMapping("/api/me")
    fun getMyProfile(@UserPrincipal userAuth: UserAuth): ResponseEntity<MyPageResponse> {
        val response = service.getMyProfile(userId = userAuth.userId)

        return ResponseEntity.ok(response)
    }
}