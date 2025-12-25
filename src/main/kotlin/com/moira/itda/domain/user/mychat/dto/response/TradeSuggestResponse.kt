package com.moira.itda.domain.user.mychat.dto.response

data class TradeSuggestResponse(
    // 거래 관련 정보
    val tradeId: String,
    val tradeStatus: String,
    val tradeType: String,
    val tradeTitle: String,
    // 제안 관련 정보
    val suggestId: Long,
    val suggestStatus: String,
    val purchaseItemId: Long?,
    val purchaseItemName: String?,
    val purchaseCount: Int?,
    val purchasePrice: Int?,
    val exchangeSellerItemId: Long?,
    val exchangeSellerItemName: String?,
    val exchangeSuggestedItemId: Long?,
    val exchangeSuggestedItemName: String?,
)
