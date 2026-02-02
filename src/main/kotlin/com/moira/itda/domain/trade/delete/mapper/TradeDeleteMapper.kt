package com.moira.itda.domain.trade.delete.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface TradeDeleteMapper {
    /**
     * 거래삭제 > 모든 TradeSuggest의 status를 DELETED로 변경 (삭제처리)
     */
    fun updateTradeSuggestStatusDeleted(tradeId: String)

    /**
     * 거래삭제 > 모든 TradeItem의 status를 DELETED로 변경 (삭제처리)
     */
    fun updateTradeItemStatusDeletedByTradeId(tradeId: String)

    /**
     * 거래삭제 > Trade의 status를 DELETED로 변경 (삭제처리)
     */
    fun updateTradeStatusDeleted(tradeId: String)
}