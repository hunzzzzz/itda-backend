package com.moira.itda.domain.suggest.mapper

import com.moira.itda.domain.suggest.dto.response.SuggestTradeItemResponse
import com.moira.itda.global.entity.TradeSuggest
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SuggestMapper {
    /**
     * 거래 아이템 목록 조회
     */
    fun selectTradeItemList(tradeId: String): List<SuggestTradeItemResponse>

    /**
     * 구매제안 > TradeItem status 조회
     * 교환제안 > TradeItem status 조회
     */
    fun selectTradeItemStatus(tradeItemId: String): String

    /**
     * 구매제안 > 구매제안 여부 확인
     */
    fun selectTradeSuggestPurchaseChk(
        userId: String,
        tradeId: String,
        tradeItemId: String,
        purchaseItemId: Long
    ): Boolean

    /**
     * 교환제안 > 교환제안 여부 확인
     */
    fun selectTradeSuggestExchangeChk(
        userId: String,
        tradeId: String,
        tradeItemId: String,
        exchangeSellerItemId: Long,
        exchangeSuggestedItemId: Long
    ): Boolean

    /**
     * 구매제안 > TradeSuggest 저장
     * 교환제안 > TradeSuggest 저장
     */
    fun insertTradeSuggest(tradeSuggest: TradeSuggest)
}