package com.moira.itda.domain.suggest.mapper

import com.moira.itda.global.entity.TradeSuggest
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SuggestMapper {
    /**
     * 거래제안 모달 > 구매제안 > TradeItem status 조회
     * 거래제안 모달 > 교환제안 > TradeItem status 조회
     */
    fun selectTradeItemStatus(tradeItemId: String): String

    /**
     * 거래제안 모달 > 구매제안 > 구매제안 여부 확인
     */
    fun selectTradeSuggestPurchaseChk(
        userId: String,
        tradeId: String,
        tradeItemId: String,
        purchaseItemId: Long
    ): Boolean

    /**
     * 거래제안 모달 > 교환제안 > 교환제안 여부 확인
     */
    fun selectTradeSuggestExchangeChk(
        userId: String,
        tradeId: String,
        tradeItemId: String,
        exchangeSellerItemId: Long,
        exchangeSuggestedItemId: Long
    ): Boolean

    /**
     * 거래제안 모달 > 구매제안 > TradeSuggest 저장
     * 거래제안 모달 > 교환제안 > TradeSuggest 저장
     */
    fun insertTradeSuggest(tradeSuggest: TradeSuggest)
}