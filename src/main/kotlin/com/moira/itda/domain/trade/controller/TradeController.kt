package com.moira.itda.domain.trade.controller

import com.moira.itda.domain.trade.dto.response.GachaIdResponse
import com.moira.itda.domain.trade.dto.request.ExchangeAddRequest
import com.moira.itda.domain.trade.service.TradeService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 가챠정보 > 가챠 목록 > 상세정보 > 교환
 */
@RestController
class TradeController(
    private val service: TradeService
) {
    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 교환 > 진행 중인 교환글 존재 여부 확인
     */
    @GetMapping("/api/gacha/{gachaId}/check/exchange")
    fun checkTradeExchange(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable gachaId: String
    ): ResponseEntity<Nothing> {
        service.checkTradeExchange(userId = userAuth.userId, gachaId = gachaId)

        return ResponseEntity.ok(null)
    }

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 판매 > 진행 중인 판매글 존재 여부 확인
     */
    @GetMapping("/api/gacha/{gachaId}/check/sales")
    fun checkTradeSales(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable gachaId: String
    ): ResponseEntity<Nothing> {
        service.checkTradeSales(userId = userAuth.userId, gachaId = gachaId)

        return ResponseEntity.ok(null)
    }

    /**
     * 교환등록
     */
    @PostMapping("/api/gacha/{gachaId}/exchange")
    fun exchange(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable gachaId: String,
        @RequestBody request: ExchangeAddRequest
    ): ResponseEntity<GachaIdResponse> {
        val response = service.exchange(userId = userAuth.userId, gachaId = gachaId, request = request)

        return ResponseEntity.ok(response)
    }
}