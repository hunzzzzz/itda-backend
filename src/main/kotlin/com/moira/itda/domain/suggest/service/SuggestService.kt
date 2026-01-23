package com.moira.itda.domain.suggest.service

import com.moira.itda.domain.notification.component.NotificationManager
import com.moira.itda.domain.suggest.component.SuggestValidator
import com.moira.itda.domain.suggest.dto.request.ExchangeSuggestRequest
import com.moira.itda.domain.suggest.dto.request.PurchaseSuggestRequest
import com.moira.itda.domain.suggest.dto.response.SuggestTradeItemResponse
import com.moira.itda.domain.suggest.mapper.SuggestMapper
import com.moira.itda.global.entity.TradeSuggest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SuggestService(
    private val mapper: SuggestMapper,
    private val notificationManager: NotificationManager,
    private val validator: SuggestValidator
) {
    /**
     * 거래 아이템 목록 조회
     */
    @Transactional(readOnly = true)
    fun getTradeItemList(tradeId: String): List<SuggestTradeItemResponse> {
        // [1] TradeItem 목록 조회
        return mapper.selectTradeItemList(tradeId = tradeId)
    }

    /**
     * 구매제안
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

        // [3] 알림 전송
        notificationManager.sendSuggestNotification(
            senderId = userId,
            tradeId = tradeId,
            tradeItemId = request.tradeItemId
        )
    }

    /**
     * 교환제안
     */
    @Transactional
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

        // [3] 알림 전송
        notificationManager.sendSuggestNotification(
            senderId = userId,
            tradeId = tradeId,
            tradeItemId = request.tradeItemId
        )
    }
}