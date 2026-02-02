package com.moira.itda.domain.suggest.add.dto.request

data class PurchaseSuggestRequest(
    val gachaId: String,
    val gachaItemId: Long,
    val content: String?,
    val originalPrice: Int,
    val discountYn: String,
    val discountPrice: Int?,
)
