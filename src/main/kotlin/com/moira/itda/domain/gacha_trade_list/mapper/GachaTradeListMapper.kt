package com.moira.itda.domain.gacha_trade_list.mapper

import com.moira.itda.domain.gacha_trade_list.dto.response.TradeListItemResponse
import com.moira.itda.domain.gacha_trade_list.dto.response.TradeListResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaTradeListMapper {
    /**
     * 가챠상세정보 > 거래목록 조회 > totalElements 계산
     */
    fun selectTradeListCnt(
        gachaId: String,
        placeId: String?,
        onlyPending: String,
        gachaItemId: Long?
    ): Long

    /**
     * 가챠상세정보 > 거래목록 조회
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
     * 가챠상세정보 > 거래목록 조회 > 거래아이템 목록 조회
     */
    fun selectTradeItemList(tradeId: String): List<TradeListItemResponse>
}