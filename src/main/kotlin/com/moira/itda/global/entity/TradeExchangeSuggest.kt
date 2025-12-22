package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class TradeExchangeSuggest(
    val id: Long?,
    val userId: String,
    val tradeId: String,
    val gachaId: String,
    val status: TradeSuggestStatus,
    val sellerItemId: Long,
    val changeYn: String,
    val originalItemId: Long,
    val suggestedItemId: Long?,
    val content: String?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)