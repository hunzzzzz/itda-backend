package com.moira.itda.domain.user.controller

import com.moira.itda.domain.user.dto.request.NicknameUpdateRequest
import com.moira.itda.domain.user.dto.request.PasswordUpdateRequest
import com.moira.itda.domain.user.dto.request.ProfileImageUpdateRequest
import com.moira.itda.domain.user.dto.request.ResetPasswordRequest
import com.moira.itda.domain.user.dto.response.MyPageResponse
import com.moira.itda.domain.user.dto.response.TokenRefreshResponse
import com.moira.itda.domain.user.service.UserService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val service: UserService
) {
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
     * 비밀번호 초기화 > 본인인증
     */
    @GetMapping("/api/reset/password/identify")
    fun identifyForResetPassword(@RequestParam email: String): ResponseEntity<Nothing?> {
        service.identifyForResetPassword(email = email)

        return ResponseEntity.ok(null)
    }

    /**
     * 비밀번호 초기화 > 본인인증 > 코드 확인
     */
    @GetMapping("/api/reset/password/identify/check/code")
    fun checkIdentifyCodeForResetPassword(
        @RequestParam email: String,
        @RequestParam code: String
    ): ResponseEntity<Nothing?> {
        service.checkIdentifyCodeForResetPassword(email = email, code = code)

        return ResponseEntity.ok(null)
    }

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

    /**
     * 마이페이지 > 내 프로필 조회
     */
    @GetMapping("/api/me")
    fun getMyProfile(@UserPrincipal userAuth: UserAuth): ResponseEntity<MyPageResponse> {
        val response = service.getMyProfile(userId = userAuth.userId)

        return ResponseEntity.ok(response)
    }

    /**
     * 마이페이지 > 프로필 사진 변경
     */
    @PutMapping("/api/me/image")
    fun updateProfileImage(
        @UserPrincipal userAuth: UserAuth,
        @RequestBody request: ProfileImageUpdateRequest
    ): ResponseEntity<Nothing?> {
        service.updateProfileImage(userId = userAuth.userId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 닉네임 변경
     */
    @PutMapping("/api/me/nickname")
    fun updateNickname(
        @UserPrincipal userAuth: UserAuth,
        @RequestBody request: NicknameUpdateRequest
    ): ResponseEntity<Nothing?> {
        service.updateNickname(userId = userAuth.userId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 마이페이지 > 비밀번호 변경
     */
    @PutMapping("/api/me/password")
    fun updatePassword(
        @UserPrincipal userAuth: UserAuth,
        @RequestBody request: PasswordUpdateRequest,
        httpRes: HttpServletResponse
    ): ResponseEntity<Nothing> {
        service.updatePassword(userId = userAuth.userId, request = request, httpRes = httpRes)

        return ResponseEntity.ok(null)
    }

    /**
     * 마이페이지 > 회원탈퇴
     */
    @DeleteMapping("/api/me/delete")
    fun delete(
        @UserPrincipal userAuth: UserAuth,
        httpRes: HttpServletResponse
    ): ResponseEntity<Nothing> {
        service.delete(userId = userAuth.userId, httpRes = httpRes)

        return ResponseEntity.ok(null)
    }
}