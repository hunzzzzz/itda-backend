package com.moira.itda.domain.admin.feedback.dto.response

import java.time.ZonedDateTime

data class AdminFeedbackResponse(
    val feedbackId: Long,
    val userId: String,
    val userNickname: String,
    val type: String,
    val fileId: String?,
    val receiveEmailYn: String,
    val createdAt: ZonedDateTime,
    val answerYn: String,
)