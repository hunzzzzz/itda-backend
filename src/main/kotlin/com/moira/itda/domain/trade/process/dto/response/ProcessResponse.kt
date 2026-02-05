package com.moira.itda.domain.trade.process.dto.response

import java.time.ZonedDateTime

data class ProcessResponse(
    // 공통
    val gachaId: String,
    val tradeId: String,
    val tradeItemId: String,
    val tradeSuggestId: String,
    val chatRoomId: String?,
    val type: String,   // '거래'의 종류
    val status: String, // '제안'의 상태
    val tradeTitle: String,
    val tradeContent: String?,
    val tradeFileId: String,
    val tradeFileUrl: String,
    val sellerId: String,
    val sellerNickname: String,
    val buyerId: String,
    val buyerNickname: String,
    val updatedAt: ZonedDateTime, // '제안'이 수정된 시점 (최근 활동 여부를 파악할 수 있음)
    // 구매제안 관련
    val purchaseItemId: Long?,
    val purchaseItemName: String?,
    val purchaseOriginalPrice: Int?,
    val purchaseDiscountYn: String?,
    val purchaseDiscountPrice: Int?,
    // 교환제안 관련
    val exchangeSellerItemId: Long?,
    val exchangeSellerItemName: String?,
    val exchangeChangeYn: String?,
    val exchangeSuggestedItemId: Long?,
    val exchangeSuggestedItemName: String?,
    // 취소 사유 (status가 CANCELED인 경우)
    val myCancelYn: String?,
    val cancelReason: String?,
    // 신고 여부 (status가 CANCELED, COMPLETED인 경우)
    val reportYn: String?,
    // 칭찬 여부 (status가 COMPLETED인 경우)
    val complimentYn: String?
)
