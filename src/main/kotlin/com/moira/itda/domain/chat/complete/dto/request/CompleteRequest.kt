package com.moira.itda.domain.chat.complete.dto.request

data class CompleteRequest(
    val tradeId: String,
    val tradeItemId: String,
    val tradeSuggestId: String,
    val gachaId: String
)
