package com.moira.itda.domain.trade_target.controller

import com.moira.itda.domain.trade_target.dto.response.TargetPageResponse
import com.moira.itda.domain.trade_target.service.TradeTargetService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 거래대상 가챠지정 모달
 */
@RestController
class TradeTargetController(
    private val service: TradeTargetService
) {
    /**
     * 거래대상 가챠지정 모달 > 즐겨찾기 목록
     */
    @GetMapping("/api/target/wish")
    fun getWishList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<TargetPageResponse> {
        val response = service.getWishList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }

    /**
     * 거래대상 가챠지정 모달 > 가챠목록
     */
    @GetMapping("/api/target/gacha")
    fun getGachaList(
        @RequestParam(required = false, defaultValue = "") keyword: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<TargetPageResponse> {
        val response = service.getGachaList(keyword = keyword, page = page)

        return ResponseEntity.ok(response)
    }
}