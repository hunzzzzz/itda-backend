package com.moira.itda.global.entity

import com.moira.itda.domain.suggest.add.dto.request.ExchangeSuggestRequest
import com.moira.itda.domain.suggest.add.dto.request.PurchaseSuggestRequest
import java.time.ZonedDateTime
import java.util.*

data class TradeSuggest(
    val id: String,
    val userId: String,
    val tradeId: String,
    val tradeItemId: String,
    val gachaId: String,
    val type: TradeSuggestType,
    val status: TradeSuggestStatus,
    val content: String?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    // 구매제안 관련
    val purchaseItemId: Long?,
    val purchaseOriginalPrice: Int?,
    val purchaseDiscountYn: String?,
    val purchaseDiscountPrice: Int?,
    // 교환제안 관련
    val exchangeSellerItemId: Long?,
    val exchangeOriginalItemId: Long?,
    val exchangeSuggestedItemId: Long?,
    val exchangeChangeYn: String?,
) {
    companion object {
        fun fromPurchaseSuggestRequest(
            userId: String,
            tradeId: String,
            tradeItemId: String,
            request: PurchaseSuggestRequest
        ): TradeSuggest {
            return TradeSuggest(
                id = UUID.randomUUID().toString(),
                userId = userId,
                tradeId = tradeId,
                tradeItemId = tradeItemId,
                gachaId = request.gachaId,
                type = TradeSuggestType.PURCHASE,
                status = TradeSuggestStatus.PENDING,
                content = request.content,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now(),
                purchaseItemId = request.gachaItemId,
                purchaseOriginalPrice = request.originalPrice,
                purchaseDiscountYn = request.discountYn,
                purchaseDiscountPrice = request.discountPrice,
                exchangeSellerItemId = null,
                exchangeOriginalItemId = null,
                exchangeSuggestedItemId = null,
                exchangeChangeYn = null
            )
        }

        fun fromExchangeSuggestRequest(
            userId: String,
            tradeId: String,
            tradeItemId: String,
            request: ExchangeSuggestRequest
        ): TradeSuggest {
            return TradeSuggest(
                id = UUID.randomUUID().toString(),
                userId = userId,
                tradeId = tradeId,
                tradeItemId = tradeItemId,
                gachaId = request.gachaId,
                type = TradeSuggestType.EXCHANGE,
                status = TradeSuggestStatus.PENDING,
                content = request.content,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now(),
                purchaseItemId = null,
                purchaseOriginalPrice = null,
                purchaseDiscountYn = null,
                purchaseDiscountPrice = null,
                exchangeSellerItemId = request.sellerItemId,
                exchangeOriginalItemId = request.originalItemId,
                exchangeSuggestedItemId = request.suggestedItemId,
                exchangeChangeYn = request.changeYn
            )
        }
    }
}