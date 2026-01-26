package com.moira.itda.domain.admin_user_feedback.mapper

import com.moira.itda.domain.admin_user_feedback.dto.response.AdminUserFeedbackResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface AdminUserFeedbackMapper {
    /**
     * 피드백 목록 조회
     */
    fun selectFeedbackList(): List<AdminUserFeedbackResponse>

    /**
     * 피드백 답변 > UserFeedback 수정
     */
    fun updateFeedbackAnswer(feedbackId: Long, answerContent: String)
}