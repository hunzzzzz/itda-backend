package com.moira.itda.domain.gacha.detail.dto.response

import java.time.ZonedDateTime

data class TradeExchangeItemResponse(
    val id: Long,
    val giveItemId: Long,
    val giveItemName: String,
    val wantItemId: Long,
    val wantItemName: String,
    val createdAt: ZonedDateTime
)
