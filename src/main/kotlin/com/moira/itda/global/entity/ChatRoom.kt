package com.moira.itda.global.entity

import java.time.ZonedDateTime
import java.util.*

data class ChatRoom(
    val id: String,
    val tradeId: String,
    val tradeSuggestId: String,
    val sellerId: String,
    val buyerId: String,
    val status: ChatStatus,
    val createdAt: ZonedDateTime
) {
    companion object {
        fun from(tradeId: String, tradeSuggestId: String, sellerId: String, buyerId: String): ChatRoom {
            return ChatRoom(
                id = UUID.randomUUID().toString(),
                tradeId = tradeId,
                tradeSuggestId = tradeSuggestId,
                sellerId = sellerId,
                buyerId = buyerId,
                status = ChatStatus.ACTIVE,
                createdAt = ZonedDateTime.now()
            )
        }
    }
}