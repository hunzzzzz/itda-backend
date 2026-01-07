package com.moira.itda.domain.user_feedback.controller

import com.moira.itda.domain.user_feedback.dto.request.FeedbackRequest
import com.moira.itda.domain.user_feedback.service.UserFeedbackService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 상단바 > 문의하기
 */
@RestController
class UserFeedbackController(
    private val service: UserFeedbackService
) {
    /**
     * 피드백
     */
    @PostMapping("/api/feedback")
    fun feedback(
        @UserPrincipal userAuth: UserAuth,
        @RequestBody request: FeedbackRequest
    ): ResponseEntity<Nothing?> {
        service.feedback(userId = userAuth.userId, request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }
}