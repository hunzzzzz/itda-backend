package com.moira.itda.global.entity

import com.moira.itda.domain.user.compliment.dto.request.ComplimentRequest
import java.time.ZonedDateTime

data class TradeUserCompliment(
    val id: Long?,
    val tradeId: String,
    val tradeItemId: String,
    val tradeSuggestId: String,
    val complimentUserId: String,
    val complimentedUserId: String,
    val complimentAt: ZonedDateTime
) {
    companion object {
        fun from(userId: String, request: ComplimentRequest): TradeUserCompliment {
            return TradeUserCompliment(
                id = null,
                tradeId = request.tradeId,
                tradeItemId = request.tradeItemId,
                tradeSuggestId = request.tradeSuggestId,
                complimentUserId = userId,
                complimentedUserId = request.opponentUserId,
                complimentAt = ZonedDateTime.now()
            )
        }
    }
}