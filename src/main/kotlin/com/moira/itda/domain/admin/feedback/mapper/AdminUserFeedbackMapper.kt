package com.moira.itda.domain.admin.feedback.mapper

import com.moira.itda.domain.admin.feedback.dto.response.AdminFeedbackDetailResponse
import com.moira.itda.domain.admin.feedback.dto.response.AdminFeedbackResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface AdminUserFeedbackMapper {
    /**
     * 유저 피드백 목록 조회
     */
    fun selectFeedbackList(): List<AdminFeedbackResponse>

    /**
     * 유저 피드백 상세조회
     */
    fun selectFeedback(feedbackId: Long): AdminFeedbackDetailResponse?
}