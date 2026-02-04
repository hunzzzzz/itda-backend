package com.moira.itda.global.entity

import com.moira.itda.domain.chat.complete.dto.request.CompleteRequest
import java.time.ZonedDateTime

data class TradeCompleteHistory(
    val id: Long?,
    val chatRoomId: String,
    val tradeId: String,
    val tradeItemId: String,
    val tradeSuggestId: String,
    val gachaId: String,
    val sellerId: String,
    val buyerId: String,
    val completedAt: ZonedDateTime
) {
    companion object {
        fun fromTradeCompleteRequest(
            chatRoomId: String,
            sellerId: String,
            buyerId: String,
            request: CompleteRequest
        ): TradeCompleteHistory {
            return TradeCompleteHistory(
                id = null,
                chatRoomId = chatRoomId,
                tradeId = request.tradeId,
                tradeItemId = request.tradeItemId,
                tradeSuggestId = request.tradeSuggestId,
                gachaId = request.gachaId,
                sellerId = sellerId,
                buyerId = buyerId,
                completedAt = ZonedDateTime.now()
            )
        }
    }
}