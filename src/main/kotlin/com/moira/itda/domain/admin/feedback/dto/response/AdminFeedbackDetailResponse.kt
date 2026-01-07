package com.moira.itda.domain.admin.feedback.dto.response

import java.time.ZonedDateTime

data class AdminFeedbackDetailResponse(
    val feedbackId: Long,
    val userId: String,
    val userNickname: String,
    val type: String,
    val content: String,
    val fileId: String?,
    val firstFileUrl: String?,
    val receiveEmailYn: String,
    val createdAt: ZonedDateTime,
    val answerContent: String?,
    val answeredAt: ZonedDateTime?
)