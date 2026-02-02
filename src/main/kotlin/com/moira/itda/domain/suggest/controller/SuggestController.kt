package com.moira.itda.domain.suggest.controller

import com.moira.itda.domain.suggest.dto.request.ExchangeSuggestRequest
import com.moira.itda.domain.suggest.dto.request.PurchaseSuggestRequest
import com.moira.itda.domain.suggest.dto.response.SuggestTradeItemResponse
import com.moira.itda.domain.suggest.service.SuggestService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 거래제안 모달
 */
@RestController
class SuggestController(
    private val service: SuggestService
) {
    /**
     * 구매제안
     */
    @PostMapping("/api/trades/{tradeId}/suggest/purchase")
    fun purchaseSuggest(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @RequestBody request: PurchaseSuggestRequest
    ): ResponseEntity<Nothing?> {
        service.purchaseSuggest(userId = userAuth.userId, tradeId = tradeId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 교환제안
     */
    @PostMapping("/api/trades/{tradeId}/suggest/exchange")
    fun exchangeSuggest(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @RequestBody request: ExchangeSuggestRequest
    ): ResponseEntity<Nothing?> {
        service.exchangeSuggest(userId = userAuth.userId, tradeId = tradeId, request = request)

        return ResponseEntity.ok(null)
    }
}