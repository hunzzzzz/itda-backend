package com.moira.itda.domain.user_temp.mysuggest.dto.response

import java.time.ZonedDateTime

data class MySuggestResponse(
    // 거래 정보
    val tradeId: String,
    val tradeStatus: String,
    val type: String,
    val userId: String,
    val userNickname: String,
    val fileId: String,
    val fileUrl: String,
    val tradeCreatedAt: ZonedDateTime,
    // 제안 정보 (공통)
    val gachaId: String,
    val suggestId: String,
    val suggestStatus: String,
    val suggestContent: String?,
    val suggestCreatedAt: ZonedDateTime,
    // 구매 제안 정보
    val purchaseGachaItemId: Long?,
    val purchaseGachaItemName: String?,
    val purchaseCount: Int?,
    val purchaseDiscountYn: String?,
    val purchaseDiscountPrice: Int?,
    // 교환 제안 정보
    val exchangeSellerItemId: Long?,
    val exchangeSellerItemName: String?,
    val exchangeSuggestedItemId: Long?,
    val exchangeSuggestedItemName: String?,
    val exchangeChangeYn: String?
)
