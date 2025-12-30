package com.moira.itda.domain.gacha_temp.detail.mapper

import com.moira.itda.domain.gacha_temp.detail.dto.response.TradeItemResponse
import com.moira.itda.domain.gacha_temp.detail.dto.response.TradeResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface GachaDetailMapper {
    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회 > totalElements 계산
     */
    fun selectTradeCnt(gachaId: String, onlyPending: String, gachaItemId: Long?): Long

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회
     */
    fun selectTradeList(
        gachaId: String,
        onlyPending: String,
        gachaItemId: Long?,
        pageSize: Int,
        offset: Int
    ): List<TradeResponse>

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회 > 하위 교환/판매 정보 조회
     */
    fun selectTradeItemList(tradeId: String, gachaId: String): List<TradeItemResponse>
}