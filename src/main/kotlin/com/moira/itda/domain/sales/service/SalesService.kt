package com.moira.itda.domain.sales.service

import com.moira.itda.domain.sales.mapper.SalesMapper
import com.moira.itda.domain.sales.dto.response.SalesItemResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SalesService(
    private val salesMapper: SalesMapper
) {
    /**
     * 판매등록 > 하위 아이템 목록 조회
     */
    @Transactional(readOnly = true)
    fun getGachaItems(gachaId: String): List<SalesItemResponse> {
        return salesMapper.selectGachaItemList(gachaId = gachaId)
    }
}