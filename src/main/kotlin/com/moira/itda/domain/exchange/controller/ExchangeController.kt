package com.moira.itda.domain.exchange.controller

import com.moira.itda.domain.exchange.dto.request.ExchangeAddRequest
import com.moira.itda.domain.exchange.dto.response.ExchangeItemResponse
import com.moira.itda.domain.exchange.dto.response.GachaIdResponse
import com.moira.itda.domain.exchange.service.ExchangeService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 교환등록 페이지
 */
@RestController
class ExchangeController(
    private val exchangeService: ExchangeService
) {
    /**
     * 교환등록 > 하위 아이템 목록 조회
     */
    @GetMapping("/api/exchange/gacha/{gachaId}/items")
    fun getGachaItems(
        @PathVariable gachaId: String
    ): ResponseEntity<List<ExchangeItemResponse>> {
        val response = exchangeService.getGachaItems(gachaId = gachaId)

        return ResponseEntity.ok(response)
    }

    /**
     * 교환 > 교환등록
     */
    @PostMapping("/api/exchange/gacha/{gachaId}")
    fun exchange(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable gachaId: String,
        @RequestBody request: ExchangeAddRequest
    ): ResponseEntity<GachaIdResponse> {
        val response = exchangeService.exchange(userId = userAuth.userId, gachaId = gachaId, request = request)

        return ResponseEntity.ok(response)
    }
}