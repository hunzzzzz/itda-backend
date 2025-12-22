package com.moira.itda.global.entity

import com.moira.itda.domain.suggest.dto.request.PurchaseSuggestRequest
import java.time.ZonedDateTime

data class TradePurchaseSuggest(
    val id: Long?,
    val userId: String,
    val tradeId: String,
    val gachaId: String,
    val gachaItemId: Long,
    val status: TradePurchaseSuggestStatus,
    val content: String?,
    val originalPrice: Int,
    val count: Int,
    val discountYn: String,
    val discountPrice: Int?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    companion object {
        fun fromPurchaseSuggestRequest(
            userId: String,
            tradeId: String,
            gachaId: String,
            request: PurchaseSuggestRequest
        ): TradePurchaseSuggest {
            return TradePurchaseSuggest(
                id = null,
                userId = userId,
                tradeId = tradeId,
                gachaId = gachaId,
                gachaItemId = request.gachaItemId,
                status = TradePurchaseSuggestStatus.PENDING,
                content = request.content,
                originalPrice = request.originalPrice,
                count = request.count,
                discountYn = request.discountYn,
                discountPrice = request.discountPrice,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )
        }
    }
}