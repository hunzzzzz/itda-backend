package com.moira.itda.domain.suggest_temp.service

import com.moira.itda.domain.suggest_temp.dto.request.ExchangeSuggestRequest
import com.moira.itda.domain.suggest_temp.mapper.TradeSuggestMapper
import com.moira.itda.global.entity.TradeStatus
import com.moira.itda.global.entity.TradeSuggest
import com.moira.itda.global.entity.TradeSuggestType
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service

@Service
class TradeSuggestService(
    private val tradeSuggestMapper: TradeSuggestMapper
) {
    /**
     * 거래 제안 모달 > 교환 제안 > 유효성 검사
     */
    fun validate(userId: String, tradeId: String, request: ExchangeSuggestRequest) {
        if (request.changeYn == "Y" && (request.suggestedItemId == request.originalItemId)) {
            throw ItdaException(ErrorCode.EXCHANGING_ITEMS_ID_SHOULD_NOT_BE_SAME_WHEN_YOU_WANT_NEGOTIATION)
        }
//        if (tradeSuggestMapper.selectTradeStatus(tradeId = tradeId) != TradeStatus.PENDING.name) {
//            throw ItdaException(ErrorCode.SUGGEST_ONLY_WHEN_TRADE_IS_PENDING)
//        }
        if (tradeSuggestMapper.selectTradeSuggestChk(
                userId = userId,
                tradeId = tradeId,
                type = TradeSuggestType.EXCHANGE.name,
                purchaseItemId = null,
                exchangeSellerItemId = request.sellerItemId,
                exchangeSuggestedItemId = request.suggestedItemId
            ) > 0
        ) {
            throw ItdaException(ErrorCode.ALREADY_SUGGESTED_EXCHANGE_ON_THE_TRADE_ITEM)
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
//        tradeSuggestMapper.insertTradeSuggest(tradeSuggest = tradeSuggest)
    }
}