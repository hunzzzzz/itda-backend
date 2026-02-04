package com.moira.itda.domain.chat.temp.controller

import com.moira.itda.domain.chat.temp.dto.request.ChatRoomTradeCancelRequest
import com.moira.itda.domain.chat.temp.dto.request.TradeCompleteRequest
import com.moira.itda.domain.chat.temp.dto.response.ChatRoomIdResponse
import com.moira.itda.domain.chat.temp.service.ChatRoomService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 채팅방
 */
@RestController
class ChatRoomController(
    private val service: ChatRoomService
) {
    /**
     * 거래취소
     */
    @PutMapping("/api/trade/chat/{chatRoomId}/cancel")
    fun cancelTrade(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable chatRoomId: String,
        @RequestBody request: ChatRoomTradeCancelRequest
    ): ResponseEntity<ChatRoomIdResponse> {
        service.cancelTrade(userId = userAuth.userId, chatRoomId = chatRoomId, request = request)

        return ResponseEntity.ok().body(ChatRoomIdResponse(chatRoomId = chatRoomId))
    }

    /**
     * 채팅방 > 거래완료
     */
    @PutMapping("/api/me/trade/chat/{chatRoomId}/complete")
    fun completeTrade(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable chatRoomId: String,
        @RequestBody request: TradeCompleteRequest
    ): ResponseEntity<Nothing> {
        service.completeTrade(userId = userAuth.userId, chatRoomId = chatRoomId, request = request)

        return ResponseEntity.ok(null)
    }
}