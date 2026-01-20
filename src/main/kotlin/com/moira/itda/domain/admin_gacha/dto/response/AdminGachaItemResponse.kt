package com.moira.itda.domain.admin_gacha.dto.response

import java.time.ZonedDateTime

data class AdminGachaItemResponse(
    val gachaItemId: Long,
    val name: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
