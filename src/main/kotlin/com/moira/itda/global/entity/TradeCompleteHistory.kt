package com.moira.itda.global.entity

import com.moira.itda.domain.chat.dto.request.TradeCompleteRequest
import java.time.ZonedDateTime

data class TradeCompleteHistory(
    val id: Long?,
    val chatRoomId: String,
    val tradeId: String,
    val tradeSuggestId: String,
    val gachaId: String,
    val sellerId: String,
    val buyerId: String,
    val completedAt: ZonedDateTime
) {
    companion object {
        fun fromTradeCompleteRequest(chatRoomId: String, request: TradeCompleteRequest): TradeCompleteHistory {
            return TradeCompleteHistory(
                id = null,
                chatRoomId = chatRoomId,
                tradeId = request.tradeId,
                tradeSuggestId = request.tradeSuggestId,
                gachaId = request.gachaId,
                sellerId = request.sellerId,
                buyerId = request.buyerId,
                completedAt = ZonedDateTime.now()
            )
        }
    }
}