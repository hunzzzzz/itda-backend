package com.moira.itda.domain.chat.dto.request

data class TradeCompleteRequest(
    val tradeId: String,
    val tradeItemId: String,
    val tradeSuggestId: String,
    val gachaId: String,
)
