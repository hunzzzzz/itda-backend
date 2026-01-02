package com.moira.itda.domain.suggest_temp.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface TradeSuggestMapper {
    /**
     * 거래 제안 모달 > 구매 제안 > 구매 제안 존재 여부 확인
     * 거래 제안 모달 > 교환 제안 > 교환 제안 존재 여부 확인
     */
    fun selectTradeSuggestChk(
        userId: String,
        tradeId: String,
        type: String,
        purchaseItemId: Long?,
        exchangeSellerItemId: Long?,
        exchangeSuggestedItemId: Long?
    ): Long
}