package com.moira.itda.domain.suggest.mapper

import com.moira.itda.domain.suggest.dto.response.ExchangeItemResponse
import com.moira.itda.domain.suggest.dto.response.GachaItemResponse
import com.moira.itda.domain.suggest.dto.response.SalesItemResponse
import com.moira.itda.global.entity.TradePurchaseSuggest
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TradeSuggestMapper {
    /**
     * 거래 제안 모달 > 판매 정보 조회
     */
    fun selectSalesItem(tradeId: String): List<SalesItemResponse>

    /**
     * 거래 제안 모달 > 구매 제안
     */
    fun insertTradePurchaseSuggest(tradePurchaseSuggest: TradePurchaseSuggest)

    /**
     * 거래 제안 모달 > 교환 정보 조회
     */
    fun selectExchangeItem(tradeId: String): List<ExchangeItemResponse>

    /**
     * 거래 제안 모달 > 하위 아이템 목록 조회
     */
    fun selectGachaItemList(tradeId: String): List<GachaItemResponse>
}