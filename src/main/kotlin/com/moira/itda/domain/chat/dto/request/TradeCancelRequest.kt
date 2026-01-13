package com.moira.itda.domain.chat.dto.request

data class TradeCancelRequest(
    val userId: String,
    val tradeId: String,
    val tradeSuggestId: String,
    val gachaId: String,
    val cancelReason: String
)
