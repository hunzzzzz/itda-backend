package com.moira.itda.domain.user.mychat.dto.request

import com.moira.itda.global.entity.TradeCancelReason

data class TradeCancelRequest(
    val userId: String,
    val cancelReason: TradeCancelReason,
    val content: String?
)
