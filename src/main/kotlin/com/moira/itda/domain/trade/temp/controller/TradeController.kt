package com.moira.itda.domain.trade.temp.controller

import com.moira.itda.domain.trade.temp.dto.request.ExchangeUpdateRequest
import com.moira.itda.domain.trade.temp.dto.request.SalesUpdateRequest
import com.moira.itda.domain.trade.temp.service.TradeService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TradeController(
    private val service: TradeService
) {
//    /**
//     * 가챠정보 > 가챠목록 > 상세정보 > 거래수정 > 거래 정보 조회
//     */
//    @GetMapping("/api/trades/{tradeId}")
//    fun getTrade(@PathVariable tradeId: String): ResponseEntity<TradeDetailContentResponse> {
//        val response = service.getTrade(tradeId = tradeId)
//
//        return ResponseEntity.ok(response)
//    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 교환수정
     */
    @PutMapping("/api/trades/{tradeId}/exchange")
    fun updateExchange(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @RequestBody request: ExchangeUpdateRequest
    ): ResponseEntity<Nothing?> {
        service.updateExchange(userId = userAuth.userId, tradeId = tradeId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 판매수정
     */
    @PutMapping("/api/trades/{tradeId}/sales")
    fun updateSales(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @RequestBody request: SalesUpdateRequest
    ): ResponseEntity<Nothing?> {
        service.updateSales(userId = userAuth.userId, tradeId = tradeId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래삭제
     */
    @DeleteMapping("/api/trades/{tradeId}/items/{tradeItemId}")
    fun delete(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @PathVariable tradeItemId: String
    ): ResponseEntity<Nothing?> {
        service.delete(userId = userAuth.userId, tradeId = tradeId, tradeItemId = tradeItemId)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }
}