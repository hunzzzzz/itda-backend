package com.moira.itda.global.entity

import com.moira.itda.domain.chat.cancel.dto.request.CancelRequest
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
        fun from(userId: String, chatRoomId: String, request: CancelRequest): TradeCancelHistory {
            return TradeCancelHistory(
                id = null,
                chatRoomId = chatRoomId,
                tradeId = request.tradeId,
                tradeSuggestId = request.tradeSuggestId,
                gachaId = request.gachaId,
                canceledUserId = userId,
                cancelReason = request.cancelReason,
                canceledAt = ZonedDateTime.now()
            )
        }
    }
}