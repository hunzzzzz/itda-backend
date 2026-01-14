package com.moira.itda.domain.user_completed_trade.controller

import com.moira.itda.domain.user_completed_trade.dto.request.ComplimentRequest
import com.moira.itda.domain.user_completed_trade.dto.response.CompletedTradePageResponse
import com.moira.itda.domain.user_completed_trade.service.UserCompletedTradeService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 내 활동 > 완료된 거래
 */
@RestController
class UserCompletedTradeController(
    private val service: UserCompletedTradeService
) {
    /**
     * 완료된 거래목록 조회
     */
    @GetMapping("/api/me/completed/trade")
    fun getCompletedTradeList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<CompletedTradePageResponse> {
        val response = service.getCompletedTradeList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }

    /**
     * 유저칭찬
     */
    @PostMapping("/api/users/compliment")
    fun compliment(
        @UserPrincipal userAuth: UserAuth,
        @RequestBody request: ComplimentRequest
    ): ResponseEntity<Nothing?> {
        service.compliment(userId = userAuth.userId, request = request)

        return ResponseEntity.ok().body(null)
    }
}