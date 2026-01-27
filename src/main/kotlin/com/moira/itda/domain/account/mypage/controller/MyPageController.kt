package com.moira.itda.domain.account.mypage.controller

import com.moira.itda.domain.account.mypage.dto.request.NicknameUpdateRequest
import com.moira.itda.domain.account.mypage.dto.request.PasswordUpdateRequest
import com.moira.itda.domain.account.mypage.dto.request.ProfileImageUpdateRequest
import com.moira.itda.domain.account.mypage.dto.response.MyPageResponse
import com.moira.itda.domain.account.mypage.service.MyPageService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 마이페이지
 */
@RestController
class MyPageController(
    private val service: MyPageService
) {
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
}