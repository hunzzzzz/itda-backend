package com.moira.itda.domain.trade.detail.controller

import com.moira.itda.domain.trade.detail.dto.response.TradeDetailContentResponse
import com.moira.itda.domain.trade.detail.service.TradeDetailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TradeDetailController(
    private val service: TradeDetailService
) {
    /**
     * 거래상세정보 조회
     */
    @GetMapping("/api/trade/{tradeId}")
    fun getTrade(@PathVariable tradeId: String): ResponseEntity<TradeDetailContentResponse> {
        val response = service.getTrade(tradeId = tradeId)

        return ResponseEntity.ok(response)
    }
}