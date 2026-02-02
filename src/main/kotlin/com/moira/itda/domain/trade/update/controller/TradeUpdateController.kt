package com.moira.itda.domain.trade.update.controller

import com.moira.itda.domain.trade.update.dto.request.ExchangeUpdateRequest
import com.moira.itda.domain.trade.update.dto.request.SalesUpdateRequest
import com.moira.itda.domain.trade.update.service.TradeUpdateService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TradeUpdateController(
    private val service: TradeUpdateService
) {
    /**
     * 교환수정
     */
    @PutMapping("/api/trade/{tradeId}/exchange")
    fun updateExchange(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @RequestBody request: ExchangeUpdateRequest
    ): ResponseEntity<Nothing?> {
        service.updateExchange(userId = userAuth.userId, tradeId = tradeId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 판매수정
     */
    @PutMapping("/api/trade/{tradeId}/sales")
    fun updateSales(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @RequestBody request: SalesUpdateRequest
    ): ResponseEntity<Nothing?> {
        service.updateSales(userId = userAuth.userId, tradeId = tradeId, request = request)

        return ResponseEntity.ok(null)
    }
}