package com.moira.itda.domain.suggest.common.mapper

import com.moira.itda.global.entity.TradeSuggest
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SuggestCommonMapper {
    /**
     * TradeItem의 status 조회
     */
    fun selectTradeItemStatus(tradeItemId: String): String

    /**
     * TradeSuggest 정보 조회 (status, userId)
     */
    fun selectTradeSuggestInfo(suggestId: String): HashMap<String, String?>

    /**
     * 구매제안 여부 조회
     */
    fun selectTradeSuggestPurchaseChk(
        userId: String,
        tradeId: String,
        tradeItemId: String,
        purchaseItemId: Long
    ): Boolean

    /**
     * 교환제안 여부 확인
     */
    fun selectTradeSuggestExchangeChk(
        userId: String,
        tradeId: String,
        tradeItemId: String,
        exchangeSellerItemId: Long,
        exchangeSuggestedItemId: Long
    ): Boolean
}