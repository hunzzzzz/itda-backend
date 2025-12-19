package com.moira.itda.domain.suggest.dto.response

import java.time.ZonedDateTime

data class SalesItemResponse(
    val tradeSalesItemId: Long,
    val gachaId: String,
    val tradeId: String,
    val gachaItemId: Long,
    val gachaItemName: String,
    val price: Int,
    val count: Int,
    val currentCount: Int,
    val createdAt: ZonedDateTime
)
