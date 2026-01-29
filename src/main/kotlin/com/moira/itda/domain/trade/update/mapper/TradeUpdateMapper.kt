package com.moira.itda.domain.trade.update.mapper

import com.moira.itda.domain.trade.add.dto.request.TradeCommonRequest
import com.moira.itda.domain.trade.update.dto.request.ExchangeItemUpdateRequest
import com.moira.itda.domain.trade.update.dto.request.SalesItemUpdateRequest
import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeItem
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface TradeUpdateMapper {
    /**
     * 거래수정 > 유효성 검사 > TradeSuggest 상태값 조회
     */
    fun selectTradeSuggestStatusChk(tradeItemId: String): HashMap<String, String>
    
    /**
     * 거래수정 > Trade 조회
     */
    fun selectTrade(tradeId: String): Trade?

    /**
     * 거래수정 > TradeItem 저장
     */
    fun insertTradeItem(tradeItem: TradeItem)

    /**
     * 거래수정 > Trade 수정
     */
    fun updateTrade(
        @Param("tradeId") tradeId: String,
        @Param("request") request: TradeCommonRequest
    )

    /**
     * 거래수정 > TradeItem 수정 (교환)
     */
    fun updateTradeItemExchange(@Param("request") request: ExchangeItemUpdateRequest)

    /**
     * 거래수정 > TradeItem 수정 (판매)
     */
    fun updateTradeItemSales(@Param("request") request: SalesItemUpdateRequest)

    /**
     * 거래수정 > TradeItem의 status를 DELETED로 변경 (삭제처리)
     */
    fun updateTradeItemStatusDeleted(tradeItemId: String)

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래삭제 > status가 DELETED가 아닌 TradeItem 존재 여부 확인
     */
    fun selectTradeItemStatusNotDeletedChk(tradeId: String): Boolean

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래삭제 > Trade의 status를 DELETED로 변경
     */
    fun updateTradeStatusDeleted(tradeId: String)
}