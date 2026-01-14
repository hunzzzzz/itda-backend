package com.moira.itda.domain.user_completed_trade.dto.request

data class ComplimentRequest(
    val tradeId: String,
    val tradeItemId: String,
    val tradeSuggestId: String,
    val opponentUserId: String
)
