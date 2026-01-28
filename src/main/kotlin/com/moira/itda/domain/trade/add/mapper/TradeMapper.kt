package com.moira.itda.domain.trade.add.mapper

import com.moira.itda.domain.trade.add.dto.request.ExchangeItemUpdateRequest
import com.moira.itda.domain.trade.add.dto.request.SalesItemUpdateRequest
import com.moira.itda.domain.trade.add.dto.request.TradeRequest
import com.moira.itda.domain.trade.add.dto.response.TradeDetailResponse
import com.moira.itda.domain.trade.add.dto.response.TradeItemResponse
import com.moira.itda.domain.trade.add.dto.response.TradeResponse
import com.moira.itda.global.entity.Trade
import com.moira.itda.global.entity.TradeItem
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface TradeMapper {
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

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래수정 > 거래 아이템 목록 조회
     * 가챠정보 > 가챠목록 > 상세정보 > 거래삭제 > 거래 아이템 목록 조회
     */
    fun selectTradeItemList(tradeId: String): List<com.moira.itda.domain.trade.add.dto.response.TradeItemResponse>

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래수정 > 거래 정보 조회
     */
    fun selectTradeDetail(tradeId: String): com.moira.itda.domain.trade.add.dto.response.TradeDetailResponse

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
        @Param("request") request: com.moira.itda.domain.trade.add.dto.request.TradeRequest
    )

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래수정 > TradeItem 수정 (교환)
     */
    fun updateTradeItemExchange(
        @Param("request") request: com.moira.itda.domain.trade.add.dto.request.ExchangeItemUpdateRequest
    )

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래수정 > TradeItem 수정 (판매)
     */
    fun updateTradeItemSales(
        @Param("request") request: com.moira.itda.domain.trade.add.dto.request.SalesItemUpdateRequest
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

    /**
     * 내 활동 > 내 거래 목록 조회 > totalElements 계산
     */
    fun selectMyTradeListCnt(userId: String, type: String): Long

    /**
     * 내 활동 > 내 거래 목록 조회
     */
    fun selectMyTradeList(userId: String, type: String, pageSize: Int, offset: Int): List<com.moira.itda.domain.trade.add.dto.response.TradeResponse>
}