package com.moira.itda.domain.sales.mapper

import com.moira.itda.domain.sales.dto.response.SalesItemResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SalesMapper {
    /**
     * 판매등록 > 하위 아이템 목록 조회
     */
    fun selectGachaItemList(gachaId: String): List<SalesItemResponse>
}