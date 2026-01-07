package com.moira.itda.domain.user_feedback.service

import com.moira.itda.domain.user_feedback.dto.request.FeedbackRequest
import com.moira.itda.domain.user_feedback.mapper.UserFeedbackMapper
import com.moira.itda.global.entity.Feedback
import com.moira.itda.global.entity.FeedbackType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserFeedbackService(
    private val mapper: UserFeedbackMapper
) {
    /**
     * 피드백
     */
    @Transactional
    fun feedback(userId: String, request: FeedbackRequest) {
        // [1] 유효성 검사 (type)
        runCatching { FeedbackType.valueOf(request.type) }
            .onFailure { throw ItdaException(ErrorCode.INVALID_FEEDBACK_TYPE) }

        // [2] 저장
        val feedback = Feedback.from(userId = userId, request = request)
        mapper.insertFeedback(feedback = feedback)
    }
}