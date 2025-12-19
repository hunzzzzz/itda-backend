package com.moira.itda.domain.suggest.dto.request

data class PurchaseSuggestRequest(
    val gachaId: String,
    val gachaItemId: Long,
    val content: String?,
    val count: Int,
    val originalPrice: Int,
    val discountYn: String,
    val discountPrice: Int?,
)
