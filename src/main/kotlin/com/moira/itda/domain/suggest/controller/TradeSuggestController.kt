package com.moira.itda.domain.suggest.controller

import com.moira.itda.domain.suggest.dto.response.SalesItemResponse
import com.moira.itda.domain.suggest.service.TradeSuggestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/**
 * 거래 제안 모달
 */
@RestController
class TradeSuggestController(
    private val tradeSuggestService: TradeSuggestService
) {
    /**
     * 거래 제안 모달 > 판매 정보 조회
     */
    @GetMapping("/api/trade/{tradeId}/suggest/sales")
    fun getSalesInfo(@PathVariable tradeId: String): ResponseEntity<List<SalesItemResponse>> {
        val response = tradeSuggestService.getSalesInfo(tradeId = tradeId)

        return ResponseEntity.ok(response)
    }
}