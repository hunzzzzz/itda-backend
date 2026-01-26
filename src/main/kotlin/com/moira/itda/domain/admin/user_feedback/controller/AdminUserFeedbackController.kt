package com.moira.itda.domain.admin.user_feedback.controller

import com.moira.itda.domain.admin.user_feedback.dto.request.AdminUserFeedbackAnswerRequest
import com.moira.itda.domain.admin.user_feedback.dto.response.AdminUserFeedbackResponse
import com.moira.itda.domain.admin.user_feedback.service.AdminUserFeedbackService
import com.moira.itda.global.auth.aop.IsAdmin
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 어드민 페이지 > 사용자 피드백 탭
 */
@RestController
class AdminUserFeedbackController(
    private val service: AdminUserFeedbackService
) {
    /**
     * 피드백 목록 조회
     */
    @IsAdmin
    @GetMapping("/api/admin/feedback")
    fun getFeedbackList(): ResponseEntity<List<AdminUserFeedbackResponse>> {
        val response = service.getFeedbackList()

        return ResponseEntity.ok(response)
    }

    /**
     * 피드백 답변
     */
    @IsAdmin
    @PostMapping("/api/admin/feedback/{feedbackId}")
    fun answer(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable feedbackId: Long,
        @RequestBody request: AdminUserFeedbackAnswerRequest
    ): ResponseEntity<Nothing?> {
        service.answer(userId = userAuth.userId, feedbackId = feedbackId, request = request)

        return ResponseEntity.ok(null)
    }
}