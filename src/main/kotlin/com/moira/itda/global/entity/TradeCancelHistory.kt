package com.moira.itda.global.entity

import com.moira.itda.domain.chat_room.dto.request.ChatRoomTradeCancelRequest
import java.time.ZonedDateTime

data class TradeCancelHistory(
    val id: Long?,
    val chatRoomId: String,
    val tradeId: String,
    val tradeSuggestId: String,
    val gachaId: String,
    val canceledUserId: String,
    val cancelReason: String,
    val canceledAt: ZonedDateTime
) {
    companion object {
        fun fromTradeCancelRequest(chatRoomId: String, request: ChatRoomTradeCancelRequest): TradeCancelHistory {
            return TradeCancelHistory(
                id = null,
                chatRoomId = chatRoomId,
                tradeId = request.tradeId,
                tradeSuggestId = request.tradeSuggestId,
                gachaId = request.gachaId,
                canceledUserId = request.userId,
                cancelReason = request.cancelReason,
                canceledAt = ZonedDateTime.now()
            )
        }
    }
}