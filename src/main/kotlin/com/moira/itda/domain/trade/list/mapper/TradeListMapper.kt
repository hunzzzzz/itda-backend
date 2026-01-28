package com.moira.itda.domain.trade.list.mapper

import com.moira.itda.domain.trade.list.dto.response.TradeItemResponse
import com.moira.itda.domain.trade.list.dto.response.TradeListResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TradeListMapper {
    /**
     * 거래목록 조회 > totalElements 계산
     */
    fun selectTradeListCnt(
        gachaId: String,
        placeId: String?,
        onlyPending: String,
        gachaItemId: Long?
    ): Long

    /**
     * 거래목록 조회
     */
    fun selectTradeList(
        gachaId: String,
        placeId: String?,
        onlyPending: String,
        gachaItemId: Long?,
        pageSize: Int,
        offset: Int
    ): List<TradeListResponse>

    /**
     * 거래목록 조회 > 거래아이템 목록 조회
     * 내 거래목록 조회 > 거래아이템 목록 조회
     */
    fun selectTradeItemList(tradeId: String): List<TradeItemResponse>

    /**
     * 내 거래목록 조회 > totalElements 계산
     */
    fun selectMyTradeListCnt(userId: String, type: String): Long

    /**
     * 내 거래목록 조회
     */
    fun selectMyTradeList(
        userId: String,
        type: String,
        pageSize: Int,
        offset: Int
    ): List<TradeListResponse>
}