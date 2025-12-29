package com.moira.itda.global.entity

import com.moira.itda.domain.trade.dto.request.SalesItemAddRequest
import com.moira.itda.domain.trade.dto.request.ExchangeItemAddRequest
import java.time.ZonedDateTime
import java.util.*

data class TradeItem(
    val id: String,
    val tradeId: String,
    val gachaId: String,
    val type: TradeItemType,
    val status: TradeStatus,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    // 구매 관련
    val salesItemId: Long?,
    val salesCount: Int?,
    val salesCurrentCount: Int?,
    val salesPrice: Int?,
    // 교환 관련
    val exchangeGiveItemId: Long?,
    val exchangeWantItemId: Long?
) {
    companion object {
        fun fromRequest(tradeId: String, gachaId: String, request: SalesItemAddRequest): TradeItem {
            return TradeItem(
                id = UUID.randomUUID().toString(),
                tradeId = tradeId,
                gachaId = gachaId,
                type = TradeItemType.SALES,
                status = TradeStatus.PENDING,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now(),
                salesItemId = request.gachaItemId,
                salesCount = request.count,
                salesCurrentCount = request.count,
                salesPrice = request.price,
                exchangeGiveItemId = null,
                exchangeWantItemId = null,
            )
        }

        fun fromRequest(tradeId: String, gachaId: String, request: ExchangeItemAddRequest): TradeItem {
            return TradeItem(
                id = UUID.randomUUID().toString(),
                tradeId = tradeId,
                gachaId = gachaId,
                type = TradeItemType.EXCHANGE,
                status = TradeStatus.PENDING,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now(),
                salesItemId = null,
                salesCount = null,
                salesCurrentCount = null,
                salesPrice = null,
                exchangeGiveItemId = request.giveItemId,
                exchangeWantItemId = request.wantItemId,
            )
        }
    }
}