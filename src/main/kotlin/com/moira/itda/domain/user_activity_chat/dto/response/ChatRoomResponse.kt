package com.moira.itda.domain.user_activity_chat.dto.response

import java.time.ZonedDateTime

data class ChatRoomResponse(
    // 채탕 관련
    val chatRoomId: String,
    val opponentId: String,
    val opponentNickname: String,
    val status: String,
    val createdAt: ZonedDateTime,
    val lastMessage: String?,
    val lastMessageAt: ZonedDateTime?,
    // 거래 관련
    val tradeId: String,
    val tradeItemId: String,
    val tradeType: String,
    val tradeTitle: String,
    // 제안 관련
    val tradeSuggestId: String,
    val tradePurchaseItemId: Long?,
    val tradePurchaseItemName: String?,
    val tradePurchasePrice: Int?,
    val tradeExchangeSellerItemId: Long?,
    val tradeExchangeSellerItemName: String?,
    val tradeExchangeSuggestedItemId: Long?,
    val tradeExchangeSuggestedItemName: String?,
    // 이미지
    val fileId: String?,
    val fileUrl: String?
)
