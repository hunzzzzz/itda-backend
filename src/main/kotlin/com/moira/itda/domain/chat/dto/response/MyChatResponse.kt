package com.moira.itda.domain.chat.dto.response

import java.time.ZonedDateTime

data class MyChatResponse(
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
    val tradeType: String,
    val tradeTitle: String
)
