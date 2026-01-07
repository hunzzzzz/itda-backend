package com.moira.itda.domain.admin.feedback.service

import com.moira.itda.domain.admin.feedback.dto.response.AdminFeedbackDetailResponse
import com.moira.itda.domain.admin.feedback.dto.response.AdminFeedbackResponse
import com.moira.itda.domain.admin.feedback.mapper.AdminUserFeedbackMapper
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminUserFeedbackService(
    private val mapper: AdminUserFeedbackMapper
) {
    /**
     * 유저 피드백 목록 조회
     */
    @Transactional(readOnly = true)
    fun getFeedbackList(): List<AdminFeedbackResponse> {
        return mapper.selectFeedbackList()
    }

    /**
     * 유저 피드백 상세조회
     */
    @Transactional(readOnly = true)
    fun getFeedback(feedbackId: Long): AdminFeedbackDetailResponse {
        return mapper.selectFeedback(feedbackId = feedbackId)
            ?: throw ItdaException(ErrorCode.FEEDBACK_NOT_FOUND)
    }

}