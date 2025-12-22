package com.moira.itda.global.entity

import com.moira.itda.domain.suggest.dto.request.ExchangeSuggestRequest
import java.time.ZonedDateTime

data class TradeExchangeSuggest(
    val id: Long?,
    val userId: String,
    val tradeId: String,
    val gachaId: String,
    val status: TradeSuggestStatus,
    val sellerItemId: Long,
    val changeYn: String,
    val originalItemId: Long,
    val suggestedItemId: Long?,
    val content: String?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    companion object {
        fun fromExchangeSuggestRequest(
            userId: String,
            tradeId: String,
            request: ExchangeSuggestRequest
        ): TradeExchangeSuggest {
            return TradeExchangeSuggest(
                id = null,
                userId = userId,
                tradeId = tradeId,
                gachaId = request.gachaId,
                status = TradeSuggestStatus.PENDING,
                sellerItemId = request.sellerItemId,
                changeYn = request.changeYn,
                originalItemId = request.originalItemId,
                suggestedItemId = request.suggestedItemId,
                content = request.content,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )
        }
    }
}