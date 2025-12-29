package com.moira.itda.domain.gacha_temp.detail.mapper

import com.moira.itda.domain.gacha_temp.detail.dto.response.TradeItemResponse
import com.moira.itda.domain.gacha_temp.detail.dto.response.TradeResponse
import com.moira.itda.global.entity.Trade
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaDetailMapper {
    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회 > totalElements 계산
     */
    fun selectTradeCnt(gachaId: String, onlyPending: String, gachaItemId: Long?): Long

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회
     */
    fun selectTradeList(
        gachaId: String,
        onlyPending: String,
        gachaItemId: Long?,
        pageSize: Int,
        offset: Int
    ): List<TradeResponse>

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회 > 하위 교환/판매 정보 조회
     */
    fun selectTradeItemList(tradeId: String, gachaId: String): List<TradeItemResponse>

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회 > 거래 삭제 > Trade 조회
     */
    fun selectTrade(gachaId: String, tradeId: String): Trade?

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회 > 거래 삭제 > APPROVED된 제안 여부 조회
     */
    fun selectTradeSuggestChk(tradeId: String): Long

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회 > 거래 삭제 > TradeSuggest 삭제
     */
    fun deleteTradeSuggest(tradeId: String)

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회 > 거래 삭제 > TradeItem 삭제
     */
    fun deleteTradeItem(tradeId: String)

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회 > 거래 삭제 > 파일 URL 목록 조회
     */
    fun selectTradeFileUrlList(tradeId: String): List<String>

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회 > 거래 삭제 > Trade 삭제
     */
    fun deleteTrade(tradeId: String)

}