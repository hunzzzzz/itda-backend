package com.moira.itda.domain.chat.temp.dto.request

data class ChatRoomTradeCancelRequest(
    val userId: String,
    val tradeId: String,
    val tradeSuggestId: String,
    val gachaId: String,
    val cancelReason: String
)