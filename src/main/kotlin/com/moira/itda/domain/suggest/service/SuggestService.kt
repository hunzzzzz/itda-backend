package com.moira.itda.domain.suggest.service

import com.moira.itda.domain.suggest.component.SuggestValidator
import com.moira.itda.domain.suggest.dto.request.ExchangeSuggestRequest
import com.moira.itda.domain.suggest.dto.request.PurchaseSuggestRequest
import com.moira.itda.domain.suggest.mapper.SuggestMapper
import com.moira.itda.global.entity.TradeSuggest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SuggestService(
    private val mapper: SuggestMapper,
    private val validator: SuggestValidator
) {
    /**
     * 거래제안 모달 > 구매제안
     */
    @Transactional
    fun purchaseSuggest(userId: String, tradeId: String, request: PurchaseSuggestRequest) {
        // [1] 유효성 검사
        validator.validatePurchaseSuggest(userId = userId, tradeId = tradeId, request = request)

        // [2] TradeSuggest 저장
        val tradeSuggest = TradeSuggest.fromPurchaseSuggestRequest(
            userId = userId,
            tradeId = tradeId,
            request = request
        )

        mapper.insertTradeSuggest(tradeSuggest = tradeSuggest)
    }

    /**
     * 거래제안 모달 > 교환제안
     */
    fun exchangeSuggest(userId: String, tradeId: String, request: ExchangeSuggestRequest) {
        // [1] 유효성 검사
        validator.validateExchangeSuggest(userId = userId, tradeId = tradeId, request = request)

        // [2] TradeSuggest 저장
        val tradeSuggest = TradeSuggest.fromExchangeSuggestRequest(
            userId = userId,
            tradeId = tradeId,
            request = request
        )
        mapper.insertTradeSuggest(tradeSuggest = tradeSuggest)
    }
}