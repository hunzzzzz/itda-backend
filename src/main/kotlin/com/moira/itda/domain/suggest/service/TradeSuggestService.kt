package com.moira.itda.domain.suggest.service

import com.moira.itda.domain.suggest.dto.request.ExchangeSuggestRequest
import com.moira.itda.domain.suggest.dto.request.PurchaseSuggestRequest
import com.moira.itda.domain.suggest.dto.response.ExchangeItemResponse
import com.moira.itda.domain.suggest.dto.response.GachaItemResponse
import com.moira.itda.domain.suggest.dto.response.SalesItemResponse
import com.moira.itda.domain.suggest.mapper.TradeSuggestMapper
import com.moira.itda.global.entity.TradeStatus
import com.moira.itda.global.entity.TradeSuggest
import com.moira.itda.global.entity.TradeSuggestType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeSuggestService(
    private val tradeSuggestMapper: TradeSuggestMapper
) {
    /**
     * 거래 제안 모달 > 판매 정보 조회
     */
    @Transactional(readOnly = true)
    fun getSalesInfo(tradeId: String): List<SalesItemResponse> {
        return tradeSuggestMapper.selectSalesItem(tradeId = tradeId)
    }

    /**
     * 거래 제안 모달 > 구매 제안 > 유효성 검사
     */
    private fun validate(userId: String, tradeId: String, request: PurchaseSuggestRequest) {
        if (request.count < 1) {
            throw ItdaException(ErrorCode.TRADE_COUNT_SHOULD_BE_LARGER_THAN_ZERO)
        }
        if (request.discountYn == "Y" && request.discountPrice == null) {
            throw ItdaException(ErrorCode.SHOULD_TYPE_PRICE_WHEN_YOU_WANT_NEGOTIATION)
        }
        if (request.discountYn == "Y" && request.originalPrice < (request.discountPrice ?: 0)) {
            throw ItdaException(ErrorCode.DISCOUNT_PRICE_SHOULD_BE_LESS_THAN_ORIGINAL_PRICE)
        }
        if (tradeSuggestMapper.selectTradeStatus(tradeId = tradeId) != TradeStatus.PENDING.name) {
            throw ItdaException(ErrorCode.SUGGEST_ONLY_WHEN_TRADE_IS_PENDING)
        }
        if (tradeSuggestMapper.selectTradeSuggestChk(
                userId = userId,
                tradeId = tradeId,
                type = TradeSuggestType.PURCHASE.name,
                purchaseItemId = request.gachaItemId,
                suggestedItemId = null,
            ) > 0
        ) {
            throw ItdaException(ErrorCode.ALREADY_SUGGESTED_PURCHASE_ON_THE_TRADE_ITEM)
        }
    }

    /**
     * 거래 제안 모달 > 구매 제안
     */
    @Transactional
    fun purchaseSuggest(userId: String, tradeId: String, request: PurchaseSuggestRequest) {
        // [1] 유효성 검사
        this.validate(userId = userId, tradeId = tradeId, request = request)

        // [2] 저장
        val tradeSuggest = TradeSuggest.fromPurchaseSuggestRequest(
            userId = userId,
            tradeId = tradeId,
            request = request
        )

        tradeSuggestMapper.insertTradeSuggest(tradeSuggest = tradeSuggest)
    }


    /**
     * 거래 제안 모달 > 교환 정보 조회
     */
    @Transactional(readOnly = true)
    fun getExchangeInfo(tradeId: String): List<ExchangeItemResponse> {
        return tradeSuggestMapper.selectExchangeItem(tradeId = tradeId)
    }

    /**
     * 거래 제안 모달 > 하위 아이템 목록 조회
     */
    @Transactional(readOnly = true)
    fun getGachaItemList(tradeId: String): List<GachaItemResponse> {
        return tradeSuggestMapper.selectGachaItemList(tradeId = tradeId)
    }

    /**
     * 거래 제안 모달 > 교환 제안 > 유효성 검사
     */
    fun validate(userId: String, tradeId: String, request: ExchangeSuggestRequest) {
        if (request.changeYn == "Y" && (request.suggestedItemId == request.originalItemId)) {
            throw ItdaException(ErrorCode.EXCHANGING_ITEMS_ID_SHOULD_NOT_BE_SAME_WHEN_YOU_WANT_NEGOTIATION)
        }
        if (tradeSuggestMapper.selectTradeStatus(tradeId = tradeId) != TradeStatus.PENDING.name) {
            throw ItdaException(ErrorCode.SUGGEST_ONLY_WHEN_TRADE_IS_PENDING)
        }
        if (request.changeYn == "Y") {
            if (tradeSuggestMapper.selectTradeSuggestChk(
                    userId = userId,
                    tradeId = tradeId,
                    type = TradeSuggestType.EXCHANGE.name,
                    purchaseItemId = null,
                    suggestedItemId = request.suggestedItemId
                ) > 0
            ) {
                throw ItdaException(ErrorCode.ALREADY_SUGGESTED_EXCHANGE_ON_THE_TRADE_ITEM)
            }
        }
    }

    /**
     * 거래 제안 모달 > 교환 제안
     */
    fun exchangeSuggest(userId: String, tradeId: String, request: ExchangeSuggestRequest) {
        // [1] 유효성 검사
        this.validate(userId = userId, tradeId = tradeId, request = request)

        // [2] 저장
        val tradeSuggest = TradeSuggest.fromExchangeSuggestRequest(
            userId = userId,
            tradeId = tradeId,
            request = request
        )
        tradeSuggestMapper.insertTradeSuggest(tradeSuggest = tradeSuggest)
    }
}