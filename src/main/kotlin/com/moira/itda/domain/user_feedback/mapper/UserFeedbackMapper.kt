package com.moira.itda.domain.user_feedback.mapper

import com.moira.itda.domain.user_feedback.dto.response.FeedbackResponse
import com.moira.itda.global.entity.Feedback
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserFeedbackMapper {
    /**
     * 피드백 > Feedback 저장
     */
    fun insertFeedback(feedback: Feedback)

    /**
     * 피드백 목록 조회 > totalElements 계산
     */
    fun selectFeedbackListCnt(userId: String): Long

    /**
     * 피드백 목록 조회
     */
    fun selectFeedbackList(userId: String, pageSize: Int, offset: Int): List<FeedbackResponse>
}