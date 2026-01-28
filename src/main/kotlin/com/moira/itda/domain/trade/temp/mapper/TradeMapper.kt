package com.moira.itda.domain.trade.temp.mapper

import com.moira.itda.domain.trade.add.dto.request.TradeCommonRequest
import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeItem
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface TradeMapper {
    /**
     * 교환등록 > TradeItem 저장
     * 판매등록 > TradeItem 저장
     */
    fun insertTradeItem(tradeItem: TradeItem)

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래수정 > Trade 조회
     * 가챠정보 > 가챠목록 > 상세정보 > 거래삭제 > Trade 조회
     */
    fun selectTrade(tradeId: String): Trade?

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래수정 > APPROVED된 제안 여부 조회
     * 가챠정보 > 가챠목록 > 상세정보 > 거래삭제 > APPROVED된 제안 여부 조회
     */
    fun selectTradeSuggestApprovedChk(tradeItemId: String): Boolean

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래수정 > PENDING 제안 여부 조회
     * 가챠정보 > 가챠목록 > 상세정보 > 거래삭제 > PENDING 제안 여부 조회
     */
    fun selectTradeSuggestPendingChk(tradeItemId: String): Boolean

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래수정 > Trade 수정
     */
    fun updateTrade(
        @Param("tradeId") tradeId: String,
        @Param("request") request: TradeCommonRequest
    )

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래수정 > TradeItem 수정 (교환)
     */
    fun updateTradeItemExchange(
        @Param("request") request: com.moira.itda.domain.trade.temp.dto.request.ExchangeItemUpdateRequest
    )

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래수정 > TradeItem 수정 (판매)
     */
    fun updateTradeItemSales(
        @Param("request") request: com.moira.itda.domain.trade.temp.dto.request.SalesItemUpdateRequest
    )

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래삭제 > TradeItem의 status를 DELETED로 변경
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