package com.moira.itda.domain.suggest.component

import com.moira.itda.domain.suggest.mapper.SuggestMapper
import com.moira.itda.domain.suggest_temp.dto.request.PurchaseSuggestRequest
import com.moira.itda.global.entity.TradeStatus
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Component

@Component
class SuggestValidator(
    private val mapper: SuggestMapper
) {
    /**
     * 거래 제안 모달 > 구매 제안 > 유효성 검사
     */
    fun validatePurchaseSuggest(userId: String, tradeId: String, request: PurchaseSuggestRequest) {
        // 제안 수량
        if (request.count < 1) {
            throw ItdaException(ErrorCode.INVALID_SUGGEST_COUNT)
        }
        // 네고 가격
        if (request.discountYn == "Y" && request.discountPrice == null) {
            throw ItdaException(ErrorCode.NO_NEGOTIATION_PRICE)
        }
        if (request.discountYn == "Y" && request.originalPrice < (request.discountPrice ?: 0)) {
            throw ItdaException(ErrorCode.INVALID_DISCOUNT_PRICE)
        }
        // 거래 상태
        if (mapper.selectTradeStatus(tradeId = tradeId) != TradeStatus.PENDING.name) {
            throw ItdaException(ErrorCode.COMPLETED_TRADE)
        }
        // 제안 여부 조회
        if (mapper.selectTradePurchaseSuggestChk(
                userId = userId,
                tradeId = tradeId,
                purchaseItemId = request.gachaItemId
            )
        ) {
            throw ItdaException(ErrorCode.ALREADY_SUGGESTED_PURCHASE)
        }
    }
}