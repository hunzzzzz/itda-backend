package com.moira.itda.domain.user_temp.mypage.controller

import com.moira.itda.domain.user_temp.mypage.dto.request.NicknameUpdateRequest
import com.moira.itda.domain.user_temp.mypage.dto.request.PasswordUpdateRequest
import com.moira.itda.domain.user_temp.mypage.dto.request.ProfileImageUpdateRequest
import com.moira.itda.domain.user_temp.mypage.dto.response.MyPageResponse
import com.moira.itda.domain.user_temp.mypage.service.MyPageService
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
    private val myPageService: MyPageService
) {
    /**
     * 마이페이지 > 내 프로필 조회
     */
    @GetMapping("/api/me")
    fun getMyProfile(@UserPrincipal userAuth: UserAuth): ResponseEntity<MyPageResponse> {
        val response = myPageService.getMyProfile(userId = userAuth.userId)

        return ResponseEntity.ok(response)
    }

    /**
     * 마이페이지 > 프로필 사진 변경
     */
    @PutMapping("/api/me/image")
    fun updateProfileImage(
        @UserPrincipal userAuth: UserAuth,
        @RequestBody request: ProfileImageUpdateRequest
    ): ResponseEntity<Nothing> {
        myPageService.updateProfileImage(userId = userAuth.userId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 마이페이지 > 닉네임 변경
     */
    @PutMapping("/api/me/nickname")
    fun updateNickname(
        @UserPrincipal userAuth: UserAuth,
        @RequestBody request: NicknameUpdateRequest
    ): ResponseEntity<Nothing> {
        myPageService.updateNickname(userId = userAuth.userId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 마이페이지 > 비밀번호 변경
     */
    @PutMapping("/api/me/password")
    fun updatePassword(
        @UserPrincipal userAuth: UserAuth,
        @RequestBody request: PasswordUpdateRequest,
        httpServletResponse: HttpServletResponse
    ): ResponseEntity<Nothing> {
        myPageService.updatePassword(
            userId = userAuth.userId,
            request = request,
            httpRes = httpServletResponse
        )

        return ResponseEntity.ok(null)
    }
}