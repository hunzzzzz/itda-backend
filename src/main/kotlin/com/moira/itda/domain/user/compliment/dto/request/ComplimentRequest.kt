package com.moira.itda.domain.user.compliment.dto.request

data class ComplimentRequest(
    val tradeId: String,
    val tradeItemId: String,
    val tradeSuggestId: String,
    val opponentUserId: String
)
