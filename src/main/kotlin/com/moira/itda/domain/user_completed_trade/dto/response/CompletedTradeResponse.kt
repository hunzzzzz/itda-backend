package com.moira.itda.domain.user_completed_trade.dto.response

data class CompletedTradeResponse(
    val tradeId: String,
    val tradeItemId: String,
    val tradeSuggestId: String,
    val opponentUserId: String,
    val opponentUserNickname: String,
    // 거래 종류
    val type: String,
    // 구매제안 관련
    val purchaseItemId: Long?,
    val purchaseItemName: String?,
    val purchaseOriginalPrice: Int?,
    val purchaseDiscountYn: String?,
    val purchaseDiscountPrice: Int?,
    // 교환제안 관련
    val exchangeSellerItemId: Long?,
    val exchangeSellerItemName: String?,
    val exchangeOriginalItemId: Long?,
    val exchangeSuggestedItemId: Long?,
    val exchangeSuggestedItemName: String?,
    val exchangeChangeYn: String?,
    // 칭찬 여부 조회
    val complimentYn: String
)
