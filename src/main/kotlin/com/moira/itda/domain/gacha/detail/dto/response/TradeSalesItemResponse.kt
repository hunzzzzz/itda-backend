package com.moira.itda.domain.gacha.detail.dto.response

import java.time.ZonedDateTime

data class TradeSalesItemResponse(
    val id: Long,
    val gachaItemId: Long,
    val gachaItemName: String,
    val count: Int,
    val currentCount: Int,
    val price: Int,
    val createdAt: ZonedDateTime
)
