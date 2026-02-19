package com.moira.itda.domain.suggest.common.component

import com.moira.itda.domain.suggest.add.dto.request.ExchangeSuggestRequest
import com.moira.itda.domain.suggest.add.dto.request.PurchaseSuggestRequest
import com.moira.itda.domain.suggest.common.mapper.SuggestCommonMapper
import com.moira.itda.global.entity.TradeItemStatus
import com.moira.itda.global.entity.TradeSuggestStatus
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Component

@Component
class SuggestValidator(
    private val mapper: SuggestCommonMapper
) {
    /**
     * 권한 검증
     */
    fun validateRole(userId: String, suggestUserId: String) {
        if (userId != suggestUserId) {
            throw ItdaException(ErrorCode.FORBIDDEN)
        }
    }

    /**
     * 네고 가격 검증
     */
    fun validateDiscountPrice(request: PurchaseSuggestRequest) {
        if (request.discountYn == "Y" && request.discountPrice == null) {
            throw ItdaException(ErrorCode.NO_NEGOTIATION_PRICE)
        }
        if (request.discountYn == "Y" && request.originalPrice < (request.discountPrice ?: 0)) {
            throw ItdaException(ErrorCode.INVALID_DISCOUNT_PRICE)
        }
    }

    /**
     * 교환 아이템 검증
     */
    fun validateExchangeItem(request: ExchangeSuggestRequest) {
        if (request.changeYn == "Y" && (request.suggestedItemId == request.originalItemId)) {
            throw ItdaException(ErrorCode.SAME_EXCHANGE_NEGOTIATION_ITEM)
        }
    }

    /**
     * TradeItem 상태값 검증
     */
    fun validateTradeItemStatus(tradeItemId: String) {
        val tradeItemStatus = mapper.selectTradeItemStatus(tradeItemId = tradeItemId)

        if (tradeItemStatus == TradeItemStatus.DELETED.name) {
            throw ItdaException(ErrorCode.FORBIDDEN)
        }
        if (tradeItemStatus == TradeItemStatus.COMPLETED.name) {
            throw ItdaException(ErrorCode.CANNOT_SUGGEST_COMPLETED_TRADE)
        }
    }

    /**
     * 제안 여부 조회 검증 (구매 제안시)
     */
    fun validateTradeSuggestChk(userId: String, tradeId: String, tradeItemId: String, purchaseItemId: Long) {
        if (mapper.selectTradeSuggestPurchaseChk(
                userId = userId,
                tradeId = tradeId,
                tradeItemId = tradeItemId,
                purchaseItemId = purchaseItemId
            )
        ) {
            throw ItdaException(ErrorCode.ALREADY_SUGGESTED_PURCHASE)
        }
    }

    /**
     * 제안 여부 조회 검증 (교환 제안시)
     */
    fun validateTradeSuggestChk(
        userId: String,
        tradeId: String,
        tradeItemId: String,
        exchangeSellerItemId: Long,
        exchangeSuggestedItemId: Long
    ) {
        if (mapper.selectTradeSuggestExchangeChk(
                userId = userId,
                tradeId = tradeId,
                tradeItemId = tradeItemId,
                exchangeSellerItemId = exchangeSellerItemId,
                exchangeSuggestedItemId = exchangeSuggestedItemId
            )
        ) {
            throw ItdaException(ErrorCode.ALREADY_SUGGESTED_EXCHANGE)
        }
    }

    /**
     * TradeSuggest 상태값 검증 (취소 시)
     */
    fun validateStatusWhenCancelBeforeResponse(suggestStatus: String) {
        when (suggestStatus) {
            TradeSuggestStatus.APPROVED.name -> {
                throw ItdaException(ErrorCode.CANNOT_CANCEL_APPROVED_SUGGEST)
            }

            TradeSuggestStatus.REJECTED.name -> {
                throw ItdaException(ErrorCode.CANNOT_CANCEL_REJECTED_SUGGEST)
            }

            TradeSuggestStatus.CANCELED.name -> {
                throw ItdaException(ErrorCode.ALREADY_CANCELED_SUGGEST)
            }

            TradeSuggestStatus.DELETED.name -> {
                throw ItdaException(ErrorCode.FORBIDDEN)
            }
        }
    }

    /**
     * TradeSuggest 상태값 검증 (삭제 시)
     */
    fun validateStatusWhenDelete(suggestStatus: String) {
        when (suggestStatus) {
            TradeSuggestStatus.APPROVED.name -> {
                throw ItdaException(ErrorCode.CANNOT_DELETE_APPROVED_SUGGEST)
            }

            TradeSuggestStatus.PENDING.name -> {
                throw ItdaException(ErrorCode.CANNOT_DELETE_PENDING_SUGGEST)
            }

            TradeSuggestStatus.DELETED.name -> {
                throw ItdaException(ErrorCode.FORBIDDEN)
            }
        }
    }
}