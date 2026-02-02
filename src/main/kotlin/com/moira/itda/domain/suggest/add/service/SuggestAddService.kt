package com.moira.itda.domain.suggest.add.service

import com.moira.itda.domain.notification.component.NotificationManager
import com.moira.itda.domain.suggest.add.dto.request.ExchangeSuggestRequest
import com.moira.itda.domain.suggest.add.dto.request.PurchaseSuggestRequest
import com.moira.itda.domain.suggest.add.mapper.SuggestAddMapper
import com.moira.itda.domain.suggest.common.component.SuggestValidator
import com.moira.itda.global.entity.TradeSuggest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SuggestAddService(
    private val mapper: SuggestAddMapper,
    private val notificationManager: NotificationManager,
    private val validator: SuggestValidator
) {
    /**
     * 구매제안 > 유효성 검사
     */
    private fun validatePurchaseSuggest(
        userId: String,
        tradeId: String,
        tradeItemId: String,
        request: PurchaseSuggestRequest
    ) {
        // 네고 가격
        validator.validateDiscountPrice(request = request)

        // TradeItem 상태값
        validator.validateTradeItemStatus(tradeItemId = tradeItemId)

        // 제안 존재 여부 확인
        validator.validateTradeSuggestChk(
            userId = userId,
            tradeId = tradeId,
            tradeItemId = tradeItemId,
            purchaseItemId = request.gachaItemId
        )
    }

    /**
     * 구매제안
     */
    @Transactional
    fun purchaseSuggest(
        userId: String,
        tradeId: String,
        tradeItemId: String,
        request: PurchaseSuggestRequest
    ) {
        // [1] 유효성 검사
        this.validatePurchaseSuggest(
            userId = userId,
            tradeId = tradeId,
            tradeItemId = tradeItemId,
            request = request
        )

        // [2] TradeSuggest 저장
        val tradeSuggest = TradeSuggest.fromPurchaseSuggestRequest(
            userId = userId,
            tradeId = tradeId,
            tradeItemId = tradeItemId,
            request = request
        )
        mapper.insertTradeSuggest(tradeSuggest = tradeSuggest)

        // [3] 알림 전송
        notificationManager.sendSuggestNotification(
            senderId = userId,
            tradeId = tradeId,
            tradeItemId = tradeItemId
        )
    }

    /**
     * 교환제안 > 유효성 검사
     */
    fun validateExchangeSuggest(
        userId: String,
        tradeId: String,
        tradeItemId: String,
        request: ExchangeSuggestRequest
    ) {
        // 교환 아이템
        validator.validateExchangeItem(request = request)

        // TradeItem 상태값
        validator.validateTradeItemStatus(tradeItemId = tradeItemId)

        // 제안 존재 여부 확인
        validator.validateTradeSuggestChk(
            userId = userId,
            tradeId = tradeId,
            tradeItemId = tradeItemId,
            exchangeSellerItemId = request.sellerItemId,
            exchangeSuggestedItemId = request.suggestedItemId
        )
    }

    /**
     * 교환제안
     */
    @Transactional
    fun exchangeSuggest(
        userId: String,
        tradeId: String,
        tradeItemId: String,
        request: ExchangeSuggestRequest
    ) {
        // [1] 유효성 검사
        this.validateExchangeSuggest(
            userId = userId,
            tradeId = tradeId,
            tradeItemId = tradeItemId,
            request = request
        )

        // [2] TradeSuggest 저장
        val tradeSuggest = TradeSuggest.fromExchangeSuggestRequest(
            userId = userId,
            tradeId = tradeId,
            tradeItemId = tradeItemId,
            request = request
        )
        mapper.insertTradeSuggest(tradeSuggest = tradeSuggest)

        // [3] 알림 전송
        notificationManager.sendSuggestNotification(
            senderId = userId,
            tradeId = tradeId,
            tradeItemId = tradeItemId
        )
    }
}