package com.moira.itda.domain.trade.dto.request

data class SalesItemUpdateRequest(
    val tradeItemId: String,
    val gachaItemId: Long,
    val count: Int,
    val price: Int
)
