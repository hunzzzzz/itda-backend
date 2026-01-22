package com.moira.itda.domain.gacha.dto.response

import java.time.ZonedDateTime

data class GachaResponse(
    val gachaId: String,
    val status: String,
    val title: String,
    val manufacturer: String,
    val fileId: String,
    val fileUrl: String,
    val viewCount: Int,
    val wishCount: Int,
    val tradeCount: Int,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
