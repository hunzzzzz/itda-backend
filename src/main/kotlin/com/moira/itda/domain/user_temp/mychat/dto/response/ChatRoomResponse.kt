package com.moira.itda.domain.user_temp.mychat.dto.response

data class ChatRoomResponse(
    // 채팅방 관련 정보
    val chatRoomId: String,
    val chatRoomStatus: String,
    // 거래 관련 정보
    val tradeId: String,
    val gachaId: String,
    val tradeStatus: String,
    val tradeType: String,
    val tradeTitle: String,
    val sellerId: String,
    val sellerNickname: String,
    val buyerId: String,
    val buyerNickname: String,
    // 제안 관련 정보
    val suggestId: String,
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
