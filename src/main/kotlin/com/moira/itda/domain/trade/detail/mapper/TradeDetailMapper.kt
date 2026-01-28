package com.moira.itda.domain.trade.detail.mapper

import com.moira.itda.domain.trade.detail.dto.response.TradeDetailResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TradeDetailMapper {
    /**
     * 거래상세정보 조회
     */
    fun selectTradeDetail(tradeId: String): TradeDetailResponse
}