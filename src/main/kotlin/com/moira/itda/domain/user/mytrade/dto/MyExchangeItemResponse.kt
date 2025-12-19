package com.moira.itda.domain.user.mytrade.dto

import java.time.ZonedDateTime

data class MyExchangeItemResponse(
    val id: Long,
    val tradeId: String,
    val gachaId: String,
    val giveItemId: Long,
    val giveItemName: String,
    val wantItemId: Long,
    val wantItemName: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
