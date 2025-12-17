package com.moira.itda.domain.gacha.detail.dto.response

import java.time.ZonedDateTime

data class GachaResponse(
    val gachaId: String,
    val status: String,
    val title: String,
    val manufacturer: String?,
    val fileId: String,
    val fileUrl: String,
    val price: Int,
    val viewCount: Int,
    val createdAt: ZonedDateTime
)
