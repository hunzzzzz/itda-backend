package com.moira.itda.domain.trade.dto.request

data class SalesItemAddRequest(
    val gachaItemId: Long,
    val count: Int,
    val price: Int
)
