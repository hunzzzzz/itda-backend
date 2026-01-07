package com.moira.itda.domain.user_feedback.mapper

import com.moira.itda.global.entity.Feedback
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserFeedbackMapper {
    /**
     * 피드백 > Feedback 저장
     */
    fun insertFeedback(feedback: Feedback)
}