package com.moira.itda.domain.trade.common.controller

import com.moira.itda.domain.trade.common.dto.response.TradeDetailContentResponse
import com.moira.itda.domain.trade.common.service.TradeCommonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TradeCommonController(
    private val service: TradeCommonService
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