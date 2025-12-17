package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class TradeSalesItem(
    val id: Long?,
    val tradeId: String,
    val gachaId: String,
    val gachaItemId: Long,
    val count: Int,
    val currentCount: Int,
    val price: Int,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)