package com.moira.itda.domain.trade.controller

import com.moira.itda.domain.trade.dto.request.ExchangeAddRequest
import com.moira.itda.domain.trade.dto.request.ExchangeUpdateRequest
import com.moira.itda.domain.trade.dto.request.SalesAddRequest
import com.moira.itda.domain.trade.dto.response.GachaIdResponse
import com.moira.itda.domain.trade.dto.response.TradeDetailContentResponse
import com.moira.itda.domain.trade.dto.response.TradeItemResponse
import com.moira.itda.domain.trade.dto.response.TradePageResponse
import com.moira.itda.domain.trade.service.TradeService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TradeController(
    private val service: TradeService
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
    fun sale(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable gachaId: String,
        @RequestBody request: SalesAddRequest
    ): ResponseEntity<GachaIdResponse> {
        val response = service.sale(userId = userAuth.userId, gachaId = gachaId, request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회
     */
    @GetMapping("/api/gacha/{gachaId}/trades")
    fun getTradeList(
        @PathVariable gachaId: String,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "N") onlyPending: String,
        @RequestParam(required = false) gachaItemId: Long?
    ): ResponseEntity<TradePageResponse> {
        val response = service.getTradeList(
            gachaId = gachaId,
            page = page,
            onlyPending = onlyPending,
            gachaItemId = gachaItemId
        )

        return ResponseEntity.ok(response)
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래수정 > 거래 아이템 목록 조회
     * 가챠정보 > 가챠목록 > 상세정보 > 거래삭제 > 거래 아이템 목록 조회
     */
    @GetMapping("/api/trades/{tradeId}/items")
    fun getTradeItemList(
        @PathVariable tradeId: String
    ): ResponseEntity<List<TradeItemResponse>> {
        val response = service.getTradeItemList(tradeId = tradeId)

        return ResponseEntity.ok(response)
    }

    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래수정 > 거래 정보 조회
     */
    @GetMapping("/api/trades/{tradeId}")
    fun getTrade(@PathVariable tradeId: String): ResponseEntity<TradeDetailContentResponse> {
        val response = service.getTrade(tradeId = tradeId)

        return ResponseEntity.ok(response)
    }

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
//
//    /**
//     * 가챠정보 > 가챠목록 > 상세정보 > 판매 수정
//     */
//    @PutMapping("/api/gacha/{gachaId}/trades/{tradeId}/sales")
//    fun updateSales(
//        @UserPrincipal userAuth: UserAuth,
//        @PathVariable tradeId: String,
//        @PathVariable gachaId: String,
//        @RequestBody request: SalesUpdateRequest
//    ): ResponseEntity<Nothing?> {
//        service.updateSales(userId = userAuth.userId, tradeId = tradeId, gachaId = gachaId, request = request)
//
//        return ResponseEntity.ok(null)
//    }
//
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

    /**
     * 내 활동 > 내 거래 목록 조회
     */
    @GetMapping("/api/me/trades")
    fun getMyTradeList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = true, defaultValue = "SALES") type: String
    ): ResponseEntity<TradePageResponse> {
        val response = service.getMyTradeList(userId = userAuth.userId, page = page, type = type)

        return ResponseEntity.ok(response)
    }

//
//    /**
//     * 거래 제안 모달 > 거래 정보 조회
//     */
//    @GetMapping("/api/trade/{tradeId}/suggest")
//    fun getTradeItem(@PathVariable tradeId: String): ResponseEntity<List<TradeItemResponse>> {
//        val response = service.getTradeItemList(tradeId = tradeId)
//
//        return ResponseEntity.ok(response)
//    }
}