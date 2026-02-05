package com.moira.itda.domain.trade.process.controller

import com.moira.itda.domain.trade.process.dto.response.ProcessPageResponse
import com.moira.itda.domain.trade.process.service.TradeProcessService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TradeProcessController(
    private val service: TradeProcessService
) {
    /**
     * 진행 중인 거래목록 조회
     */
    @GetMapping("/api/me/trade/process")
    fun getTradeProcessList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(value = "page", defaultValue = "1") page: Int,
    ): ResponseEntity<ProcessPageResponse> {
        val response = service.getTradeProcessList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }
}