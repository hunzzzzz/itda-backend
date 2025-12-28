package com.moira.itda.domain.suggest_temp.dto.request

data class PurchaseSuggestRequest(
    val gachaId: String,
    val gachaItemId: Long,
    val tradeItemId: String,
    val content: String?,
    val count: Int,
    val originalPrice: Int,
    val discountYn: String,
    val discountPrice: Int?,
)
