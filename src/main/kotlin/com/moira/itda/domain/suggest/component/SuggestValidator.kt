package com.moira.itda.domain.suggest.component

import com.moira.itda.domain.suggest.dto.request.ExchangeSuggestRequest
import com.moira.itda.domain.suggest.dto.request.PurchaseSuggestRequest
import com.moira.itda.domain.suggest.mapper.SuggestMapper
import com.moira.itda.global.entity.TradeStatus
import com.moira.itda.global.entity.TradeSuggestStatus
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Component

@Component
class SuggestValidator(
    private val mapper: SuggestMapper
) {
    /**
     * 거래제안 모달 > 구매제안 > 유효성 검사
     */
    fun validatePurchaseSuggest(userId: String, tradeId: String, request: PurchaseSuggestRequest) {
        // 네고 가격
        if (request.discountYn == "Y" && request.discountPrice == null) {
            throw ItdaException(ErrorCode.NO_NEGOTIATION_PRICE)
        }
        if (request.discountYn == "Y" && request.originalPrice < (request.discountPrice ?: 0)) {
            throw ItdaException(ErrorCode.INVALID_DISCOUNT_PRICE)
        }
        // 거래 항목의 status가 PENDING이어야 한다.
        if (mapper.selectTradeItemStatus(tradeItemId = request.tradeItemId) != TradeStatus.PENDING.name) {
            throw ItdaException(ErrorCode.COMPLETED_TRADE)
        }
        // 이미 해당 거래항목에 제안을 한 경우, 또 다시 제안을 할 수 없다. (DELETED인 TradeSuggest는 제외)
        if (mapper.selectTradeSuggestPurchaseChk(
                userId = userId,
                tradeId = tradeId,
                tradeItemId = request.tradeItemId,
                purchaseItemId = request.gachaItemId
            )
        ) {
            throw ItdaException(ErrorCode.ALREADY_SUGGESTED_PURCHASE)
        }
    }

    /**
     * 거래제안 모달 > 교환제안 > 유효성 검사
     */
    fun validateExchangeSuggest(userId: String, tradeId: String, request: ExchangeSuggestRequest) {
        // 다른 아이템으로 교환제안 시, 원래 교환 희망 아이템 != 제안 희망 아이템
        if (request.changeYn == "Y" && (request.suggestedItemId == request.originalItemId)) {
            throw ItdaException(ErrorCode.SAME_EXCHANGE_NEGOTIATION_ITEM)
        }
        // 거래 항목의 status가 PENDING이어야 한다.
        if (mapper.selectTradeItemStatus(tradeItemId = request.tradeItemId) != TradeStatus.PENDING.name) {
            throw ItdaException(ErrorCode.COMPLETED_TRADE)
        }
        // 제안 여부 조회
        if (mapper.selectTradeSuggestExchangeChk(
                userId = userId,
                tradeId = tradeId,
                tradeItemId = request.tradeItemId,
                exchangeSellerItemId = request.sellerItemId,
                exchangeSuggestedItemId = request.suggestedItemId
            )
        ) {
            throw ItdaException(ErrorCode.ALREADY_SUGGESTED_EXCHANGE)
        }
    }

    /**
     * 내 활동 > 제안 > 제안취소 > 유효성 검사
     */
    fun validateCancelSuggest(suggestStatus: String, userId: String, suggestUserId: String) {
        // [1] status에 대한 유효성 검사
        when (suggestStatus) {
            TradeSuggestStatus.APPROVED.name -> {
                throw ItdaException(ErrorCode.CANNOT_CANCEL_APPROVED_SUGGEST)
            }

            TradeSuggestStatus.REJECTED.name -> {
                throw ItdaException(ErrorCode.CANNOT_CANCEL_REJECTED_SUGGEST)
            }

            TradeSuggestStatus.CANCELED_BEFORE_RESPONSE.name,
            TradeSuggestStatus.CANCELED.name -> {
                throw ItdaException(ErrorCode.ALREADY_CANCELED_SUGGEST)
            }

            TradeSuggestStatus.DELETED.name -> {
                throw ItdaException(ErrorCode.ALREADY_DELETED_SUGGEST)
            }
        }

        // [2] 권한에 대한 유효성 검사
        if (userId != suggestUserId) {
            throw ItdaException(ErrorCode.OTHERS_SUGGEST)
        }
    }
}