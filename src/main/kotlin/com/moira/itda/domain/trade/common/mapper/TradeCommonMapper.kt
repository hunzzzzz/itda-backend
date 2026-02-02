package com.moira.itda.domain.trade.common.mapper

import com.moira.itda.global.entity.Trade
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TradeCommonMapper {
    /**
     * Trade 조회
     */
    fun selectTrade(tradeId: String): Trade?

    /**
     * COMPLETED인 TradeItem이 있는지 확인
     */
    fun selectTradeItemStatusCompletedChk(tradeId: String): Boolean

    /**
     * PENDING, APPROVED, COMPLETED인 TradeSuggest가 있는지 확인
     */
    fun selectTradeSuggestStatusChkByTradeId(tradeId: String): HashMap<String, Long>

    /**
     * 거래수정 > 유효성 검사 > TradeSuggest 상태값 조회
     */
    fun selectTradeSuggestStatusChk(tradeItemId: String): HashMap<String, String>
}