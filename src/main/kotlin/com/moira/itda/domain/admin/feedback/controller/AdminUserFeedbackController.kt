package com.moira.itda.domain.admin.feedback.controller

import com.moira.itda.domain.admin.feedback.dto.response.AdminFeedbackDetailResponse
import com.moira.itda.domain.admin.feedback.dto.response.AdminFeedbackResponse
import com.moira.itda.domain.admin.feedback.service.AdminUserFeedbackService
import com.moira.itda.global.auth.aop.IsAdmin
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/**
 * 어드민 페이지 > 유저 피드백
 */
@RestController
class AdminUserFeedbackController(
    private val service: AdminUserFeedbackService
) {
    /**
     * 유저 피드백 목록 조회
     */
    @IsAdmin
    @GetMapping("/api/admin/feedback")
    fun getFeedbackList(): ResponseEntity<List<AdminFeedbackResponse>> {
        val response = service.getFeedbackList()

        return ResponseEntity.ok(response)
    }

    /**
     * 유저 피드백 상세조회
     */
    @IsAdmin
    @GetMapping("/api/admin/feedback/{feedbackId}")
    fun getFeedback(@PathVariable feedbackId: Long): ResponseEntity<AdminFeedbackDetailResponse> {
        val response = service.getFeedback(feedbackId = feedbackId)

        return ResponseEntity.ok(response)
    }
}