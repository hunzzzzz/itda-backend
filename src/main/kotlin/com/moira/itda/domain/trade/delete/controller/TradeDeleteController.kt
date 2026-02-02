package com.moira.itda.domain.trade.delete.controller

import com.moira.itda.domain.trade.delete.service.TradeDeleteService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TradeDeleteController(
    private val service: TradeDeleteService
) {
    /**
     * 거래삭제
     */
    @DeleteMapping("/api/trade/{tradeId}")
    fun delete(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String
    ): ResponseEntity<Nothing?> {
        service.delete(userId = userAuth.userId, tradeId = tradeId)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
    }
}