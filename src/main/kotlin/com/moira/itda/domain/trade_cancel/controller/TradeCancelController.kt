package com.moira.itda.domain.trade_cancel.controller

import com.moira.itda.domain.trade_cancel.dto.request.TradeCancelRequest
import com.moira.itda.domain.trade_cancel.dto.response.ChatRoomIdResponse
import com.moira.itda.domain.trade_cancel.service.TradeCancelService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 거래취소 모달
 */
@RestController
class TradeCancelController(
    private val service: TradeCancelService
) {
    /**
     * 거래취소
     */
    @PutMapping("/api/trade/chat/{chatRoomId}/cancel")
    fun cancelTrade(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable chatRoomId: String,
        @RequestBody request: TradeCancelRequest
    ): ResponseEntity<ChatRoomIdResponse> {
        service.cancelTrade(userId = userAuth.userId, chatRoomId = chatRoomId, request = request)

        return ResponseEntity.ok().body(ChatRoomIdResponse(chatRoomId = chatRoomId))
    }
}