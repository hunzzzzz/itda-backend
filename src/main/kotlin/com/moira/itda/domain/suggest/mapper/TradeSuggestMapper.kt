package com.moira.itda.domain.suggest.mapper

import com.moira.itda.domain.suggest.dto.response.ExchangeItemResponse
import com.moira.itda.domain.suggest.dto.response.GachaItemResponse
import com.moira.itda.domain.suggest.dto.response.SalesItemResponse
import com.moira.itda.global.entity.TradeExchangeSuggest
import com.moira.itda.global.entity.TradePurchaseSuggest
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TradeSuggestMapper {
    /**
     * 거래 제안 모달 > 구매 제안 > 거래 status 조회
     * 거래 제안 모달 > 교환 제안 > 거래 status 조회
     */
    fun selectTradeStatus(tradeId: String): String

    /**
     * 거래 제안 모달 > 판매 정보 조회
     */
    fun selectSalesItem(tradeId: String): List<SalesItemResponse>

    /**
     * 거래 제안 모달 > 구매 제안 > 구매 제안 존재 여부 확인
     */
    fun selectTradePurchaseSuggestChk(userId: String, tradeId: String, gachaItemId: Long): Long

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

    /**
     * 거래 제안 모달 > 교환 제안 > 교환 제안 존재 여부 확인
     */
    fun selectTradeExchangeSuggestChk(userId: String, tradeId: String, suggestedItemId: Long): Long

    /**
     * 거래 제안 모달 > 교환 제안 > TradeExchangeSuggest 저장
     */
    fun insertTradeExchangeSuggest(tradeExchangeSuggest: TradeExchangeSuggest)
}