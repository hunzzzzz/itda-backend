package com.moira.itda.domain.suggest.controller

import com.moira.itda.domain.suggest.dto.request.SalesSuggestRequest
import com.moira.itda.domain.suggest.dto.response.SalesItemResponse
import com.moira.itda.domain.suggest.service.TradeSuggestService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    /**
     * 거래 제안 모달 > 구매 제안
     */
    @PostMapping("/api/trade/{tradeId}/suggest/sales")
    fun suggest(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @RequestBody request: SalesSuggestRequest
    ): ResponseEntity<Nothing> {
        tradeSuggestService.suggest(userId = userAuth.userId, tradeId = tradeId, request = request)

        return ResponseEntity.ok(null)
    }
}