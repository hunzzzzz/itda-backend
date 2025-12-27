package com.moira.itda.domain.user.mychat.dto.request

import com.moira.itda.global.entity.TradeCancelReason

data class TradeCancelRequest(
    val userId: String,
    val tradeId: String,
    val tradeSuggestId: String,
    val gachaId: String,
    val cancelReason: TradeCancelReason,
    val content: String?
)
