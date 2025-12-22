package com.moira.itda.domain.suggest.controller

import com.moira.itda.domain.suggest.dto.request.ExchangeSuggestRequest
import com.moira.itda.domain.suggest.dto.request.PurchaseSuggestRequest
import com.moira.itda.domain.suggest.dto.response.ExchangeItemResponse
import com.moira.itda.domain.suggest.dto.response.GachaItemResponse
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
    @PostMapping("/api/trade/{tradeId}/suggest/purchase")
    fun purchaseSuggest(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @RequestBody request: PurchaseSuggestRequest
    ): ResponseEntity<Nothing> {
        tradeSuggestService.purchaseSuggest(userId = userAuth.userId, tradeId = tradeId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 거래 제안 모달 > 교환 정보 조회
     */
    @GetMapping("/api/trade/{tradeId}/suggest/exchange")
    fun getExchangeInfo(@PathVariable tradeId: String): ResponseEntity<List<ExchangeItemResponse>> {
        val response = tradeSuggestService.getExchangeInfo(tradeId = tradeId)

        return ResponseEntity.ok(response)
    }

    /**
     * 거래 제안 모달 > 하위 아이템 목록 조회
     */
    @GetMapping("/api/trade/{tradeId}/gacha/items")
    fun getGachaItems(@PathVariable tradeId: String): ResponseEntity<List<GachaItemResponse>> {
        val response = tradeSuggestService.getGachaItemList(tradeId = tradeId)

        return ResponseEntity.ok(response)
    }

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