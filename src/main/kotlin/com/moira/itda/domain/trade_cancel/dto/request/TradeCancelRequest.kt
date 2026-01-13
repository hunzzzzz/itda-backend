package com.moira.itda.domain.trade_cancel.dto.request

data class TradeCancelRequest(
    val userId: String,
    val tradeId: String,
    val tradeSuggestId: String,
    val gachaId: String,
    val cancelReason: String
)