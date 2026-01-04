package com.moira.itda.domain.suggest.dto.request

data class PurchaseSuggestRequest(
    val gachaId: String,
    val gachaItemId: Long,
    val tradeItemId: String,
    val content: String?,
    val originalPrice: Int,
    val discountYn: String,
    val discountPrice: Int?,
)
