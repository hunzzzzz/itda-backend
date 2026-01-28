package com.moira.itda.domain.admin.support.dto.response

import java.time.ZonedDateTime

data class SupportResponse(
    val supportId: Long,
    val userId: String,
    val userNickname: String,
    val type: String,
    val content: String,
    val fileId: String?,
    val fileUrl: String?,
    val createdAt: ZonedDateTime,
    val answerContent: String?,
    val answeredAt: ZonedDateTime?,
)