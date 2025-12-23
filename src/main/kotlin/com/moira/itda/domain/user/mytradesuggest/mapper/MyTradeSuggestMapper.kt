package com.moira.itda.domain.user.mytradesuggest.mapper

import com.moira.itda.domain.user.mytradesuggest.dto.response.ExchangeSuggestResponse
import com.moira.itda.domain.user.mytradesuggest.dto.response.PurchaseSuggestResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MyTradeSuggestMapper {
    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 목록 조회 > 거래 type 조회
     */
    fun selectTradeType(tradeId: String): String?

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 목록 조회 > 구매 제안 목록 조회 > totalElements 계산
     */
    fun selectTradePurchaseSuggestListCnt(tradeId: String): Long

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 목록 조회 > 구매 제안 목록 조회
     */
    fun selectTradePurchaseSuggestList(
        tradeId: String,
        pageSize: Int,
        offset: Int
    ): List<PurchaseSuggestResponse>

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 목록 조회 > 교환 제안 목록 조회 > totalElements 계산
     */
    fun selectTradeExchangeSuggestListCnt(tradeId: String): Long

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 목록 조회 > 교환 제안 목록 조회
     */
    fun selectTradeExchangeSuggestList(
        tradeId: String,
        pageSize: Int,
        offset: Int
    ): List<ExchangeSuggestResponse>
}