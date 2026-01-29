package com.moira.itda.domain.trade.update.dto.request

data class ExchangeItemUpdateRequest(
    val tradeItemId: String,
    val giveItemId: Long,
    val wantItemId: Long
)
