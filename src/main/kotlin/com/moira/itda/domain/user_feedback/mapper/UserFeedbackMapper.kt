package com.moira.itda.domain.user_feedback.mapper

import com.moira.itda.domain.user_feedback.dto.response.FeedbackResponse
import com.moira.itda.global.entity.UserFeedback
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserFeedbackMapper {
    /**
     * 피드백 등록 > Feedback 저장
     */
    fun insertFeedback(userFeedback: UserFeedback)

    /**
     * 피드백 목록 조회 > totalElements 계산
     */
    fun selectFeedbackListCnt(userId: String): Long

    /**
     * 피드백 목록 조회
     */
    fun selectFeedbackList(userId: String, pageSize: Int, offset: Int): List<FeedbackResponse>
}