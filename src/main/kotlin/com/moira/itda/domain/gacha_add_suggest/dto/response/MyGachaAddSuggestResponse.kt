package com.moira.itda.domain.gacha_add_suggest.dto.response

import java.time.ZonedDateTime

data class MyGachaAddSuggestResponse(
    val id: String,
    val status: String,
    val content: String,
    val fileId: String?,
    val fileUrl: String?,
    val adminComment: String?,
    val receiveEmailYn: String,
    val requestedAt: ZonedDateTime,
    val processedAt: ZonedDateTime?
)
