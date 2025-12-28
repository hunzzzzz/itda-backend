package com.moira.itda.domain.user_temp.mypage.controller

import com.moira.itda.domain.user_temp.mypage.dto.request.PasswordUpdateRequest
import com.moira.itda.domain.user_temp.mypage.service.MyPageService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
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