package com.moira.itda.domain.trade.add.mapper

import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeItem
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TradeAddMapper {
    /**
     * 교환등록 > Trade 저장
     * 판매등록 > Trade 저장
     */
    fun insertTrade(trade: Trade)

    /**
     * 교환등록 > TradeItem 저장
     * 판매등록 > TradeItem 저장
     */
    fun insertTradeItem(tradeItem: TradeItem)
}