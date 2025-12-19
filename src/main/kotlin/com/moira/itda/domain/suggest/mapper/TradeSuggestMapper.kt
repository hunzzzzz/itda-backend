package com.moira.itda.domain.suggest.mapper

import com.moira.itda.domain.suggest.dto.response.SalesItemResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TradeSuggestMapper {
    /**
     * 거래 제안 모달 > 판매 정보 조회
     */
    fun selectSalesItem(tradeId: String): List<SalesItemResponse>
}