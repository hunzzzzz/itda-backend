package com.moira.itda.domain.user.support.dto.response

import java.time.ZonedDateTime

data class SupportResponse(
    val supportId: String,
    val userId: String,
    val type: String,
    val content: String,
    val fileId: String?,
    var fileUrls: List<String>?,
    val createdAt: ZonedDateTime,
    val answerContent: String?,
    val answeredAt: ZonedDateTime?
)