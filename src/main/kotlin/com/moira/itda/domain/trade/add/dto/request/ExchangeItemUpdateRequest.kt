package com.moira.itda.domain.trade.add.dto.request

data class ExchangeItemUpdateRequest(
    val tradeItemId: String,
    val giveItemId: Long,
    val wantItemId: Long
)
