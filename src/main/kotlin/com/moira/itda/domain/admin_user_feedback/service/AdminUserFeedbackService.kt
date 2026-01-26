package com.moira.itda.domain.admin_user_feedback.service

import com.moira.itda.domain.admin_user_feedback.dto.request.AdminUserFeedbackAnswerRequest
import com.moira.itda.domain.admin_user_feedback.dto.response.AdminUserFeedbackResponse
import com.moira.itda.domain.admin_user_feedback.mapper.AdminUserFeedbackMapper
import com.moira.itda.domain.notification.component.NotificationManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminUserFeedbackService(
    private val mapper: AdminUserFeedbackMapper,
    private val notificationManager: NotificationManager
) {
    /**
     * 피드백 목록 조회
     */
    @Transactional(readOnly = true)
    fun getFeedbackList(): List<AdminUserFeedbackResponse> {
        return mapper.selectFeedbackList()
    }

    /**
     * 피드백 답변
     */
    @Transactional
    fun answer(userId: String, feedbackId: Long, request: AdminUserFeedbackAnswerRequest) {
        // [1] Feedback 수정
        mapper.updateFeedbackAnswer(feedbackId = feedbackId, answerContent = request.content)

        // [2] 알림 전송 (비동기)
        notificationManager.sendFeedbackAnswerNotification(senderId = userId, feedbackId = feedbackId)
    }
}