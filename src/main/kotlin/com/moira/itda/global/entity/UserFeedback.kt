package com.moira.itda.global.entity

import com.moira.itda.domain.user_feedback.dto.request.FeedbackRequest
import java.time.ZonedDateTime

data class UserFeedback(
    val id: Long?,
    val userId: String,
    val type: UserFeedbackType,
    val content: String,
    val fileId: String?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    val answerContent: String?,
    val answeredAt: ZonedDateTime?
) {
    companion object {
        fun from(userId: String, request: FeedbackRequest): UserFeedback {
            return UserFeedback(
                id = null,
                userId = userId,
                type = UserFeedbackType.valueOf(request.type),
                content = request.content,
                fileId = request.fileId,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now(),
                answerContent = null,
                answeredAt = null
            )
        }
    }
}