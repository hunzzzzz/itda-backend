package com.moira.itda.domain.admin.gacha.dto.response

import java.time.ZonedDateTime

data class AdminGachaResponse(
    val gachaId: String,
    val status: String,
    val title: String,
    val manufacturer: String?,
    val fileId: String,
    val fileUrl: String,
    val price: Int,
    val viewCount: Int,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
