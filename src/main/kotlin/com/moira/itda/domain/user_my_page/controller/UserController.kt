package com.moira.itda.domain.user_my_page.controller

import com.moira.itda.domain.user_my_page.dto.request.NicknameUpdateRequest
import com.moira.itda.domain.user_my_page.dto.request.PasswordUpdateRequest
import com.moira.itda.domain.user_my_page.dto.request.ProfileImageUpdateRequest
import com.moira.itda.domain.user_my_page.dto.response.MyPageResponse
import com.moira.itda.domain.user_my_page.service.UserService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 마이페이지
 */
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
     * 내 프로필 조회
     */
    @GetMapping("/api/me")
    fun getMyProfile(@UserPrincipal userAuth: UserAuth): ResponseEntity<MyPageResponse> {
        val response = service.getMyProfile(userId = userAuth.userId)

        return ResponseEntity.ok(response)
    }

    /**
     * 프로필 사진 변경
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
     * 비밀번호 변경
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
     * 회원탈퇴
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