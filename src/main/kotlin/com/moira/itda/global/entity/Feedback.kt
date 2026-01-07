package com.moira.itda.global.entity

import com.moira.itda.domain.user_feedback.dto.request.FeedbackRequest
import java.time.ZonedDateTime

data class Feedback(
    val userId: String,
    val type: FeedbackType,
    val content: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    val answerContent: String?,
    val answeredAt: ZonedDateTime?
) {
    companion object {
        fun from(userId: String, request: FeedbackRequest): Feedback {
            return Feedback(
                userId = userId,
                type = FeedbackType.valueOf(request.type),
                content = request.content,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now(),
                answerContent = null,
                answeredAt = null
            )
        }
    }
}