package com.moira.itda.domain.suggest.service

import com.moira.itda.domain.suggest.dto.request.SalesSuggestRequest
import com.moira.itda.domain.suggest.dto.response.SalesItemResponse
import com.moira.itda.domain.suggest.mapper.TradeSuggestMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeSuggestService(
    private val tradeSuggestMapper: TradeSuggestMapper
) {
    /**
     * 거래 제안 모달 > 판매 정보 조회
     */
    @Transactional(readOnly = true)
    fun getSalesInfo(tradeId: String): List<SalesItemResponse> {
        return tradeSuggestMapper.selectSalesItem(tradeId = tradeId)
    }

    /**
     * 거래 제안 모달 > 구매 제안
     */
    @Transactional
    fun suggest(userId: String, tradeId: String, request: SalesSuggestRequest) {

    }
}