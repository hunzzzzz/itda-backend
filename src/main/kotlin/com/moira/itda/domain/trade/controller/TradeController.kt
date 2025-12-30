package com.moira.itda.domain.trade.controller

import com.moira.itda.domain.trade.dto.request.ExchangeAddRequest
import com.moira.itda.domain.trade.dto.request.ExchangeUpdateRequest
import com.moira.itda.domain.trade.dto.request.SalesAddRequest
import com.moira.itda.domain.trade.dto.response.GachaIdResponse
import com.moira.itda.domain.trade.service.TradeService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.HttpStatus
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

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    /**
     * 판매등록
     */
    @PostMapping("/api/gacha/{gachaId}/sales")
    fun sale(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable gachaId: String,
        @RequestBody request: SalesAddRequest
    ): ResponseEntity<GachaIdResponse> {
        val response = service.sale(userId = userAuth.userId, gachaId = gachaId, request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 교환 수정
     */
    @PutMapping("/api/gacha/{gachaId}/trades/{tradeId}/exchange")
    fun updateExchange(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @PathVariable gachaId: String,
        @RequestBody request: ExchangeUpdateRequest
    ): ResponseEntity<Nothing?> {
        service.updateExchange(userId = userAuth.userId, tradeId = tradeId, gachaId = gachaId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 거래 삭제
     */
    @DeleteMapping("/api/gacha/{gachaId}/trades/{tradeId}")
    fun deleteTrade(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable gachaId: String,
        @PathVariable tradeId: String
    ): ResponseEntity<Nothing?> {
        service.deleteTrade(userId = userAuth.userId, tradeId = tradeId)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }
}