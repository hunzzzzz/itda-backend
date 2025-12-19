package com.moira.itda.domain.user.mytrade.controller

import com.moira.itda.domain.user.mytrade.dto.MyTradePageResponse
import com.moira.itda.domain.user.mytrade.service.MyTradeService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 마이페이지 > 내 거래 목록 조회
 */
@RestController
class MyTradeController(
    private val myTradeService: MyTradeService
) {
    /**
     * 마이페이지 > 내 거래 목록 조회
     */
    @GetMapping("/api/me/trade")
    fun getTrades(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = true, defaultValue = "SALES") type: String
    ): ResponseEntity<MyTradePageResponse> {
        val response = myTradeService.getTrades(userId = userAuth.userId, page = page, type = type)

        return ResponseEntity.ok(response)
    }
}