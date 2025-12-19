package com.moira.itda.global.entity

import com.moira.itda.domain.exchange.dto.request.ExchangeItemAddRequest
import java.time.ZonedDateTime

data class TradeExchangeItem(
    val id: Long?,
    val tradeId: String,
    val gachaId: String,
    val giveItemId: Long,
    val wantItemId: Long,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    companion object {
        fun fromExchangeItemAddRequest(
            tradeId: String,
            gachaId: String,
            request: ExchangeItemAddRequest
        ): TradeExchangeItem {
            return TradeExchangeItem(
                id = null,
                tradeId = tradeId,
                gachaId = gachaId,
                giveItemId = request.giveItemId,
                wantItemId = request.wantItemId,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )
        }
    }
}
