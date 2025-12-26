package com.moira.itda.domain.suggest.dto.response

import java.time.ZonedDateTime

data class ExchangeItemResponse(
    val tradeItemId: String,
    val tradeId: String,
    val gachaId: String,
    val exchangeGiveItemId: Long,
    val exchangeGiveItemName: String,
    val exchangeWantItemId: Long,
    val exchangeWantItemName: String,
    val createdAt: ZonedDateTime
)
