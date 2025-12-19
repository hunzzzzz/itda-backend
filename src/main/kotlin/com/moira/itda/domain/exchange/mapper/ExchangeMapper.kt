package com.moira.itda.domain.exchange.mapper

import com.moira.itda.domain.exchange.dto.response.ExchangeItemResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ExchangeMapper {
    /**
     * 교환등록 > 하위 아이템 목록 조회
     */
    fun selectGachaItemList(gachaId: String): List<ExchangeItemResponse>

}