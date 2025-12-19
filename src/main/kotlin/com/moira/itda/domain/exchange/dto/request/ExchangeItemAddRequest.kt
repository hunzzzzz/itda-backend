package com.moira.itda.domain.exchange.dto.request

data class ExchangeItemAddRequest(
    val giveItemId: Long,
    val wantItemId: Long
)
