package com.moira.itda.domain.trade.mapper

import com.moira.itda.domain.trade.dto.request.ExchangeItemUpdateRequest
import com.moira.itda.domain.trade.dto.request.SalesItemUpdateRequest
import com.moira.itda.domain.trade.dto.request.TradeRequest
import com.moira.itda.domain.trade.dto.response.TradeDetailResponse
import com.moira.itda.domain.trade.dto.response.TradeItemResponse
import com.moira.itda.domain.trade.dto.response.TradeResponse
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

    // ---------------------------------------------------------------------------------- //

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 목록 조회 > totalElements 계산
     */
    fun selectTradeListCnt(gachaId: String, onlyPending: String, gachaItemId: Long?): Long

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 목록 조회
     */
    fun selectTradeList(
        gachaId: String,
        onlyPending: String,
        gachaItemId: Long?,
        pageSize: Int,
        offset: Int
    ): List<TradeResponse>

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 목록 조회 > 하위 교환/판매 정보 조회
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 수정 > 거래 정보 조회 > 하위 교환/판매 정보 조회
     * 내 활동 > 내 거래 목록 조회 > 하위 교환/판매 정보 조회
     * 거래 제안 모달 > 거래 정보 조회
     */
    fun selectTradeItem(tradeId: String): List<TradeItemResponse>

    // ---------------------------------------------------------------------------------- //

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 수정 > 거래 정보 조회
     */
    fun selectTradeResponse(tradeId: String): TradeDetailResponse

    // ---------------------------------------------------------------------------------- //

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 수정 > Trade 수정
     */
    fun updateTrade(
        @Param("tradeId") tradeId: String,
        @Param("request") request: TradeRequest
    )

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 수정 > TradeItem 수정 (교환)
     */
    fun updateTradeExchangeItem(@Param("request") request: ExchangeItemUpdateRequest)

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 수정 > TradeItem 수정 (판매)
     */
    fun updateTradeSalesItem(@Param("request") request: SalesItemUpdateRequest)

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 수정 > Trade 조회
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 삭제 > Trade 조회
     */
    fun selectTrade(tradeId: String): Trade?

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 수정 > APPROVED된 제안 여부 조회
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 삭제 > APPROVED된 제안 여부 조회
     */
    fun selectTradeSuggestApprovedChk(tradeId: String): Boolean

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 수정 > PENDING 제안 여부 조회
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 삭제 > PENDING 제안 여부 조회
     */
    fun selectTradeSuggestPendingChk(tradeId: String): Boolean

    // ---------------------------------------------------------------------------------- //

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 삭제 > TradeSuggest 삭제
     */
    fun deleteTradeSuggest(tradeId: String)

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 삭제 > TradeItem 삭제
     */
    fun deleteTradeItem(tradeId: String)

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 삭제 > Trade 삭제
     */
    fun deleteTrade(tradeId: String)

    // ---------------------------------------------------------------------------------- //

    /**
     * 내 활동 > 내 거래 목록 조회 > totalElements 계산
     */
    fun selectMyTradeListCnt(userId: String, type: String): Long

    /**
     * 내 활동 > 내 거래 목록 조회
     */
    fun selectMyTradeList(userId: String, type: String, pageSize: Int, offset: Int): List<TradeResponse>
}