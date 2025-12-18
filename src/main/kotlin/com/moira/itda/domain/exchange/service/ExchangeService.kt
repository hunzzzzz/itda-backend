package com.moira.itda.domain.exchange.service

import com.moira.itda.domain.exchange.dto.response.SalesItemResponse
import com.moira.itda.domain.exchange.mapper.ExchangeMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExchangeService(
    private val exchangeMapper: ExchangeMapper
) {
    /**
     * 교환등록 > 하위 아이템 목록 조회
     */
    @Transactional(readOnly = true)
    fun getGachaItems(gachaId: String): List<SalesItemResponse> {
        return exchangeMapper.selectGachaItemList(gachaId = gachaId)
    }

}