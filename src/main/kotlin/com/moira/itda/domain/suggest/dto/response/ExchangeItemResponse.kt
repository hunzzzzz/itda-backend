package com.moira.itda.domain.suggest.dto.response

import java.time.ZonedDateTime

data class ExchangeItemResponse(
    val tradeExchangeItemId: Long,
    val gachaId: String,
    val tradeId: String,
    val giveItemId: Long,
    val giveItemName: String,
    val wantItemId: Long,
    val wantItemName: String,
    val createdAt: ZonedDateTime
)
