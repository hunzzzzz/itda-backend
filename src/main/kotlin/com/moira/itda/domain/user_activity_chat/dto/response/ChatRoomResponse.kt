package com.moira.itda.domain.user_activity_chat.dto.response

import java.time.ZonedDateTime

data class ChatRoomResponse(
    // 채탕 관련
    val chatRoomId: String,
    val sellerId: String,
    val sellerNickname: String,
    val buyerId: String,
    val buyerNickname: String,
    val status: String,
    val createdAt: ZonedDateTime,
    val lastMessage: String?,
    val lastMessageAt: ZonedDateTime?,
    // 거래 관련
    val tradeId: String,
    val tradeItemId: String,
    val tradeType: String,
    val tradeTitle: String,
    // 이미지
    val fileId: String?,
    val fileUrl: String?
)
