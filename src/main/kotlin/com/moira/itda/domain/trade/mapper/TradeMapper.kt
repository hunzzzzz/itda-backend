package com.moira.itda.domain.trade.mapper

import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeItem
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TradeMapper {
    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 교환 > 진행 중인 교환글 존재 여부 확인
     */
    fun selectTradeExchangeChk(userId: String, gachaId: String): Boolean

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 판매 > 진행 중인 판매글 존재 여부 확인
     */
    fun selectTradeSalesChk(userId: String, gachaId: String): Boolean

    /**
     * 교환등록 > Trade 저장
     */
    fun insertTrade(trade: Trade)

    /**
     * 교환등록 > TradeItem 저장
     */
    fun insertTradeItem(tradeItem: TradeItem)
}