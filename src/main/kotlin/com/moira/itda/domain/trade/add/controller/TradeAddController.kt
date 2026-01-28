package com.moira.itda.domain.trade.add.controller

import com.moira.itda.domain.trade.add.dto.request.ExchangeAddRequest
import com.moira.itda.domain.trade.add.dto.request.SalesAddRequest
import com.moira.itda.domain.trade.add.service.TradeAddService
import com.moira.itda.domain.trade.temp.dto.response.GachaIdResponse
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TradeAddController(
    private val service: TradeAddService
) {
    /**
     * 교환등록
     */
    @PostMapping("/api/gacha/{gachaId}/trade/exchange")
    fun exchange(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable gachaId: String,
        @RequestBody request: ExchangeAddRequest
    ): ResponseEntity<GachaIdResponse> {
        val response = service.exchange(userId = userAuth.userId, gachaId = gachaId, request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    /**
     * 판매등록
     */
    @PostMapping("/api/gacha/{gachaId}/trade/sales")
    fun sales(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable gachaId: String,
        @RequestBody request: SalesAddRequest
    ): ResponseEntity<GachaIdResponse> {
        val response = service.sales(userId = userAuth.userId, gachaId = gachaId, request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}