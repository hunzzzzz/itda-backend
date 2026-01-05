package com.moira.itda.domain.suggest.dto.response

import java.time.ZonedDateTime

data class MyTradeSuggestResponse(
    // 제안 관련 (공통)
    val suggestId: String,
    val tradeId: String,
    val tradeItemId: String,
    val gachaId: String,
    val type: String,
    val status: String,
    val userId: String,
    val userNickname: String,
    val content: String?,
    val createdAt: ZonedDateTime,
    // 거래 관련
    val fileId: String,
    val fileUrl: String,
    // 구매제안 관련
    val purchaseItemId: Long?,
    val purchaseItemName: String?,
    val purchaseDiscountYn: String?,
    val purchaseDiscountPrice: Int?,
    // 교환제안 관련
    val exchangeSellerItemId: Long?,
    val exchangeSellerItemName: String?,
    val exchangeChangeYn: String?,
    val exchangeSuggestedItemId: Long?,
    val exchangeSuggestedItemName: String?,
    // 취소 사유 (TradeSuggest가 CANCELED인 경우에만)
    val cancelReason: String?
)