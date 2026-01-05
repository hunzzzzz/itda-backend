package com.moira.itda.domain.suggest.controller

import com.moira.itda.domain.suggest.dto.request.ExchangeSuggestRequest
import com.moira.itda.domain.suggest.dto.request.PurchaseSuggestRequest
import com.moira.itda.domain.suggest.dto.request.TradeSuggestYnRequest
import com.moira.itda.domain.suggest.dto.response.ChatRoomIdResponse
import com.moira.itda.domain.suggest.dto.response.MyTradeSuggestPageResponse
import com.moira.itda.domain.suggest.dto.response.TradeSuggestPageResponse
import com.moira.itda.domain.suggest.service.SuggestService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class SuggestController(
    private val service: SuggestService
) {
    /**
     * 거래제안 모달 > 구매제안
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
     * 거래제안 모달 > 교환제안
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

    /**
     * 제안목록 조회 모달 > 제안목록 조회
     */
    @GetMapping("/api/trades/{tradeId}/suggests")
    fun getSuggestList(
        @PathVariable tradeId: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<TradeSuggestPageResponse> {
        val response = service.getSuggestList(tradeId = tradeId, page = page)

        return ResponseEntity.ok(response)
    }

    /**
     * 제안목록 조회 모달 > 제안승인
     */
    @PostMapping("/api/trades/{tradeId}/suggests/approve")
    fun approve(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @RequestBody request: TradeSuggestYnRequest
    ): ResponseEntity<ChatRoomIdResponse> {
        val response = service.approve(
            userId = userAuth.userId,
            tradeId = tradeId,
            request = request
        )

        return ResponseEntity.ok(response)
    }

    /**
     * 제안목록 조회 모달 > 제안거절
     */
    @PostMapping("/api/trades/{tradeId}/suggests/reject")
    fun reject(
        @PathVariable tradeId: String,
        @RequestBody request: TradeSuggestYnRequest
    ): ResponseEntity<Nothing> {
        service.reject(tradeId = tradeId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 내 활동 > 제안 > 내 제안목록 조회
     */
    @GetMapping("/api/me/trade/suggest")
    fun getMySuggestList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<MyTradeSuggestPageResponse> {
        val response = service.getMySuggestList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }
}