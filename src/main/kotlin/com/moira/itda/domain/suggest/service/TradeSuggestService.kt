package com.moira.itda.domain.suggest.service

import com.moira.itda.domain.suggest.dto.request.PurchaseSuggestRequest
import com.moira.itda.domain.suggest.dto.response.ExchangeItemResponse
import com.moira.itda.domain.suggest.dto.response.GachaItemResponse
import com.moira.itda.domain.suggest.dto.response.SalesItemResponse
import com.moira.itda.domain.suggest.mapper.TradeSuggestMapper
import com.moira.itda.global.entity.TradePurchaseSuggest
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
    private fun validate(request: PurchaseSuggestRequest) {
        if (request.count < 1) {
            throw ItdaException(ErrorCode.TRADE_COUNT_SHOULD_BE_LARGER_THAN_ZERO)
        }
        if (request.discountYn == "Y" && request.discountPrice == null) {
            throw ItdaException(ErrorCode.SHOULD_TYPE_PRICE_WHEN_YOU_WANT_NEGOTIATION)
        }
        if (request.discountYn == "Y" && request.originalPrice < (request.discountPrice ?: 0)) {
            throw ItdaException(ErrorCode.DISCOUNT_PRICE_SHOULD_BE_LESS_THAN_ORIGINAL_PRICE)
        }
    }

    /**
     * 거래 제안 모달 > 구매 제안
     */
    @Transactional
    fun suggest(userId: String, tradeId: String, request: PurchaseSuggestRequest) {
        // [1] 유효성 검사
        this.validate(request = request)

        // [2] 저장
        val tradePurchaseSuggest = TradePurchaseSuggest.fromPurchaseSuggestRequest(
            userId = userId,
            tradeId = tradeId,
            gachaId = request.gachaId,
            request = request
        )

        tradeSuggestMapper.insertTradePurchaseSuggest(tradePurchaseSuggest = tradePurchaseSuggest)
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
}