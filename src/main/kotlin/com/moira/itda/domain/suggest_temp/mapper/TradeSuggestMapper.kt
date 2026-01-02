package com.moira.itda.domain.suggest_temp.mapper

import com.moira.itda.domain.suggest_temp.dto.response.GachaItemResponse
import com.moira.itda.global.entity.TradeSuggest
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TradeSuggestMapper {
    /**
     * 거래 제안 모달 > 구매 제안 > 거래 status 조회
     * 거래 제안 모달 > 교환 제안 > 거래 status 조회
     */
    fun selectTradeStatus(tradeId: String): String

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

    /**
     * 거래 제안 모달 > 하위 아이템 목록 조회
     */
    fun selectGachaItemList(tradeId: String): List<GachaItemResponse>

    /**
     * 거래 제안 모달 > 구매 제안 > TradeSuggest 저장
     * 거래 제안 모달 > 교환 제안 > TradeSuggest 저장
     */
    fun insertTradeSuggest(tradeSuggest: TradeSuggest)
}