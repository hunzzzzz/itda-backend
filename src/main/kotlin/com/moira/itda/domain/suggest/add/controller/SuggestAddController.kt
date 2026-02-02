package com.moira.itda.domain.suggest.add.controller

import com.moira.itda.domain.suggest.add.dto.request.ExchangeSuggestRequest
import com.moira.itda.domain.suggest.add.dto.request.PurchaseSuggestRequest
import com.moira.itda.domain.suggest.add.service.SuggestAddService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 거래제안 모달
 */
@RestController
class SuggestAddController(
    private val service: SuggestAddService
) {
    /**
     * 구매제안
     */
    @PostMapping("/api/trade/{tradeId}/items/{tradeItemId}/suggest/purchase")
    fun purchaseSuggest(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @PathVariable tradeItemId: String,
        @RequestBody request: PurchaseSuggestRequest
    ): ResponseEntity<Nothing?> {
        service.purchaseSuggest(
            userId = userAuth.userId,
            tradeId = tradeId,
            tradeItemId = tradeItemId,
            request = request
        )

        return ResponseEntity.ok(null)
    }

    /**
     * 교환제안
     */
    @PostMapping("/api/trade/{tradeId}/items/{tradeItemId}/suggest/exchange")
    fun exchangeSuggest(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @PathVariable tradeItemId: String,
        @RequestBody request: ExchangeSuggestRequest
    ): ResponseEntity<Nothing?> {
        service.exchangeSuggest(
            userId = userAuth.userId,
            tradeId = tradeId,
            tradeItemId = tradeItemId,
            request = request
        )

        return ResponseEntity.ok(null)
    }
}