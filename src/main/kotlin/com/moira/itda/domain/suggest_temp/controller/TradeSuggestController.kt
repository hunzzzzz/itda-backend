package com.moira.itda.domain.suggest_temp.controller

import com.moira.itda.domain.suggest_temp.dto.request.ExchangeSuggestRequest
import com.moira.itda.domain.suggest_temp.service.TradeSuggestService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 거래 제안 모달
 */
@RestController
class TradeSuggestController(
    private val tradeSuggestService: TradeSuggestService
) {
    /**
     * 거래 제안 모달 > 교환 제안
     */
    @PostMapping("/api/trade/{tradeId}/suggest/exchange")
    fun exchangeSuggest(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @RequestBody request: ExchangeSuggestRequest
    ): ResponseEntity<Nothing> {
        tradeSuggestService.exchangeSuggest(userId = userAuth.userId, tradeId = tradeId, request = request)

        return ResponseEntity.ok(null)
    }
}