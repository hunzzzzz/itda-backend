package com.moira.itda.domain.user.support.controller

import com.moira.itda.domain.user.support.dto.request.SupportRequest
import com.moira.itda.domain.user.support.dto.response.SupportPageResponse
import com.moira.itda.domain.user.support.service.UserSupportService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 상단바 > 문의하기
 */
@RestController
class UserSupportController(
    private val service: UserSupportService
) {
    /**
     * 문의 등록
     */
    @PostMapping("/api/support")
    fun addUserSupport(
        @UserPrincipal userAuth: UserAuth,
        @RequestBody request: SupportRequest
    ): ResponseEntity<Nothing?> {
        service.addUserSupport(userId = userAuth.userId, request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }

    /**
     * 문의 목록 조회
     */
    @GetMapping("/api/me/support")
    fun getSupportList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<SupportPageResponse> {
        val response = service.getSupportList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }
}