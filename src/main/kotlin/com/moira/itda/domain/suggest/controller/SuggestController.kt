package com.moira.itda.domain.suggest.controller

import com.moira.itda.domain.suggest.dto.request.TradeSuggestYnRequest
import com.moira.itda.domain.suggest.dto.response.ChatRoomIdResponse
import com.moira.itda.domain.suggest.dto.response.TradeSuggestPageResponse
import com.moira.itda.domain.suggest.service.SuggestService
import com.moira.itda.domain.suggest_temp.dto.request.PurchaseSuggestRequest
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 내 활동 > 판매/교환 > 제안 목록 조회 모달
 */
@RestController
class SuggestController(
    private val service: SuggestService
) {
    /**
     * 거래 제안 모달 > 구매 제안
     */
    @PostMapping("/api/trade/{tradeId}/suggest/purchase")
    fun purchaseSuggest(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @RequestBody request: PurchaseSuggestRequest
    ): ResponseEntity<Nothing?> {
        service.purchaseSuggest(userId = userAuth.userId, tradeId = tradeId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 내 활동 > 판매/교환 > 제안 목록 조회 모달 > 제안 목록 조회
     */
    @GetMapping("/api/me/trades/{tradeId}/suggests")
    fun getSuggestList(
        @PathVariable tradeId: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<TradeSuggestPageResponse> {
        val response = service.getSuggestList(tradeId = tradeId, page = page)

        return ResponseEntity.ok(response)
    }

    /**
     * 내 활동 > 판매/교환 > 제안 목록 조회 모달 > 제안 승인
     */
    @PostMapping("/api/me/trades/{tradeId}/suggests/approve")
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
     * 내 활동 > 판매/교환 > 제안 목록 조회 모달 > 제안 거절
     */
    @PostMapping("/api/me/trades/{tradeId}/suggests/reject")
    fun reject(
        @PathVariable tradeId: String,
        @RequestBody request: TradeSuggestYnRequest
    ): ResponseEntity<Nothing> {
        service.reject(tradeId = tradeId, request = request)

        return ResponseEntity.ok(null)
    }
}