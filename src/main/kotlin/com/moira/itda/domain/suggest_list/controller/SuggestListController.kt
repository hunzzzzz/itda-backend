package com.moira.itda.domain.suggest_list.controller

import com.moira.itda.domain.suggest_list.dto.response.ChatRoomIdResponse
import com.moira.itda.domain.suggest_list.dto.response.TradeSuggestPageResponse
import com.moira.itda.domain.suggest_list.dto.request.TradeSuggestYnRequest
import com.moira.itda.domain.suggest_list.service.SuggestListService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 제안목록 조회 모달
 */
@RestController
class SuggestListController(
    private val service: SuggestListService
) {
    /**
     * 제안목록 조회
     */
    @GetMapping("/api/trades/{tradeId}/suggests")
    fun getSuggestList(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<TradeSuggestPageResponse> {
        val response = service.getSuggestList(
            userId = userAuth.userId,
            tradeId = tradeId,
            page = page
        )

        return ResponseEntity.ok(response)
    }

    /**
     * 제안승인
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
     * 제안거절
     */
    @PostMapping("/api/trades/{tradeId}/suggests/reject")
    fun reject(
        @PathVariable tradeId: String,
        @RequestBody request: TradeSuggestYnRequest
    ): ResponseEntity<Nothing> {
        service.reject(tradeId = tradeId, request = request)

        return ResponseEntity.ok(null)
    }
}