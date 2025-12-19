package com.moira.itda.domain.user.mytrade.dto

import java.time.ZonedDateTime

data class MySalesItemResponse(
    val id: Long,
    val tradeId: String,
    val gachaId: String,
    val gachaItemId: Long,
    val gachaItemName: String,
    val count: Int,
    val currentCount: Int,
    val price: Int,
    val createdAt: ZonedDateTime
)
