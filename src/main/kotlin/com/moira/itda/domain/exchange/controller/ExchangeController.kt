package com.moira.itda.domain.exchange.controller

import com.moira.itda.domain.exchange.dto.response.ExchangeItemResponse
import com.moira.itda.domain.exchange.service.ExchangeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/**
 * 교환등록 페이지
 */
@RestController
class ExchangeController(
    private val exchangeService: ExchangeService
) {
    /**
     * 교환등록 > 하위 아이템 목록 조회
     */
    @GetMapping("/api/exchange/gacha/{gachaId}/items")
    fun getGachaItems(
        @PathVariable gachaId: String
    ): ResponseEntity<List<ExchangeItemResponse>> {
        val response = exchangeService.getGachaItems(gachaId = gachaId)

        return ResponseEntity.ok(response)
    }

}