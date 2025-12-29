package com.moira.itda.domain.trade.dto.request

data class ExchangeItemAddRequest(
    val giveItemId: Long,
    val wantItemId: Long
)
