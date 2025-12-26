package com.moira.itda.domain.suggest.dto.response

import java.time.ZonedDateTime

data class SalesItemResponse(
    val tradeItemId: String,
    val tradeId: String,
    val gachaId: String,
    val salesItemId: Long,
    val salesItemName: String,
    val price: Int,
    val count: Int,
    val currentCount: Int,
    val createdAt: ZonedDateTime
)
