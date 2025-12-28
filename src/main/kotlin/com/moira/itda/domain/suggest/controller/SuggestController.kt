package com.moira.itda.domain.suggest.controller

import com.moira.itda.domain.suggest.dto.response.TradeSuggestPageResponse
import com.moira.itda.domain.suggest.service.SuggestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 내 활동 > 판매/교환 > 제안 목록 조회 모달
 */
@RestController
class SuggestController(
    private val service: SuggestService
) {
    /**
     * 내 활동 > 판매/교환 > 제안 목록 조회 모달 > 제안 목록 조회
     */
    @GetMapping("/api/me/trade/{tradeId}/suggests")
    fun getMyTradeSuggests(
        @PathVariable tradeId: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<TradeSuggestPageResponse> {
        val response = service.getSuggestList(tradeId = tradeId, page = page)

        return ResponseEntity.ok(response)
    }
}