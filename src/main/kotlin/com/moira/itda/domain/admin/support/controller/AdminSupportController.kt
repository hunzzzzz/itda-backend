package com.moira.itda.domain.admin.support.controller

import com.moira.itda.domain.admin.support.dto.request.AnswerRequest
import com.moira.itda.domain.admin.support.dto.response.SupportResponse
import com.moira.itda.domain.admin.support.service.AdminSupportService
import com.moira.itda.global.auth.aop.IsAdmin
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 어드민 페이지 > 사용자 피드백 탭
 */
@RestController
class AdminSupportController(
    private val service: AdminSupportService
) {
    /**
     * 문의 목록 조회
     */
    @IsAdmin
    @GetMapping("/api/admin/support")
    fun getUserSupportList(): ResponseEntity<List<SupportResponse>> {
        val response = service.getUserSupportList()

        return ResponseEntity.ok(response)
    }

    /**
     * 문의 답변
     */
    @IsAdmin
    @PostMapping("/api/admin/support/{supportId}")
    fun answer(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable supportId: Long,
        @RequestBody request: AnswerRequest
    ): ResponseEntity<Nothing?> {
        service.answer(userId = userAuth.userId, supportId = supportId, request = request)

        return ResponseEntity.ok(null)
    }
}