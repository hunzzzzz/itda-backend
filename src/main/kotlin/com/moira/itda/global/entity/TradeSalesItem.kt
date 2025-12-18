package com.moira.itda.global.entity

import com.moira.itda.domain.sales.dto.request.SalesItemAddRequest
import java.time.ZonedDateTime

data class TradeSalesItem(
    val id: Long?,
    val tradeId: String,
    val gachaId: String,
    val gachaItemId: Long,
    val count: Int,
    val currentCount: Int,
    val price: Int,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    companion object {
        fun fromSalesItemAddRequest(gachaId: String, tradeId: String, request: SalesItemAddRequest): TradeSalesItem {
            return TradeSalesItem(
                id = null,
                tradeId = tradeId,
                gachaId = gachaId,
                gachaItemId = request.gachaItemId,
                count = request.count,
                currentCount = request.count,
                price = request.price,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )
        }
    }
}