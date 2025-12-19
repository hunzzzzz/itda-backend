package com.moira.itda.global.entity

data class TradePurchaseSuggestItem(
    val id: Long?,
    val userId: String,
    val tradeId: String,
    val gachaId: String,
    val gachaItemId: Long,
    val content: String?,
    val originalPrice: Int,
    val count: Int,
    val discountYn: String,
    val discountAmount: Int?,
    val createdAt: String,
    val updatedAt: String
)