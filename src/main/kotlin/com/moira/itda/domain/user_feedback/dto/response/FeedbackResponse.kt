package com.moira.itda.domain.user_feedback.dto.response

import java.time.ZonedDateTime

data class FeedbackResponse(
    val userId: String,
    val type: String,
    val content: String,
    val createdAt: ZonedDateTime,
    val answerContent: String?,
    val answeredAt: ZonedDateTime?
)
