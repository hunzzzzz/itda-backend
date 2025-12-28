package com.moira.itda.domain.user_temp.mychat.dto.request

data class TradeCompleteRequest(
    val tradeId: String,
    val tradeItemId: String,
    val tradeSuggestId: String,
    val gachaId: String,
)
