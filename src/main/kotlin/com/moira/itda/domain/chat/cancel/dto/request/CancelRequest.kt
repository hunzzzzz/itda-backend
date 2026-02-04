package com.moira.itda.domain.chat.cancel.dto.request

data class CancelRequest(
    val tradeId: String,
    val tradeSuggestId: String,
    val gachaId: String,
    val cancelReason: String
)