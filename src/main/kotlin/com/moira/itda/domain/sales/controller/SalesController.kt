package com.moira.itda.domain.sales.controller

import com.moira.itda.domain.sales.dto.request.SalesAddRequest
import com.moira.itda.domain.sales.dto.response.SalesItemResponse
import com.moira.itda.domain.sales.dto.response.GachaIdResponse
import com.moira.itda.domain.sales.service.SalesService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 판매등록 페이지
 */
@RestController
class SalesController(
    private val salesService: SalesService
) {
    /**
     * 판매등록 > 하위 아이템 목록 조회
     */
    @GetMapping("/api/sales/gacha/{gachaId}/items")
    fun getGachaItems(
        @PathVariable gachaId: String
    ): ResponseEntity<List<SalesItemResponse>> {
        val response = salesService.getGachaItems(gachaId = gachaId)

        return ResponseEntity.ok(response)
    }

    /**
     * 판매등록
     */
    @PostMapping("/api/sales/gacha/{gachaId}")
    fun sale(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable gachaId: String,
        @RequestBody request: SalesAddRequest
    ): ResponseEntity<GachaIdResponse> {
        val response = salesService.sale(userId = userAuth.userId, gachaId = gachaId, request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}