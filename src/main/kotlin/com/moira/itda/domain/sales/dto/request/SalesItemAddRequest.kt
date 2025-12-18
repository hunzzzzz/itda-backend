package com.moira.itda.domain.sales.dto.request

data class SalesItemAddRequest(
    val salesItemId: Long,
    val count: Int,
    val price: Int
)
