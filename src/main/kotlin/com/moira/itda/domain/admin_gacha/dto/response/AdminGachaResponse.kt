package com.moira.itda.domain.admin_gacha.dto.response

import java.time.ZonedDateTime

data class AdminGachaResponse(
    val gachaId: String,
    val status: String,
    val title: String,
    val manufacturer: String,
    val fileId: String,
    val fileUrl: String,
    val viewCount: Int,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
