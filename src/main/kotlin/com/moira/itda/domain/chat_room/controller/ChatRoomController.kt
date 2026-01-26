package com.moira.itda.domain.chat_room.controller

import com.moira.itda.domain.chat_room.dto.request.ChatMessageRequest
import com.moira.itda.domain.chat_room.dto.request.TradeCompleteRequest
import com.moira.itda.domain.chat_room.dto.response.ChatMessageResponse
import com.moira.itda.domain.chat_room.dto.response.ChatRoomDetailResponse
import com.moira.itda.domain.chat_room.service.ChatRoomService
import com.moira.itda.domain.chat_room.dto.request.ChatRoomTradeCancelRequest
import com.moira.itda.domain.chat_room.dto.response.ChatRoomIdResponse
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

/**
 * 채팅방
 */
@RestController
class ChatRoomController(
    private val service: ChatRoomService
) {
    /**
     * 채팅방 > 거래제안 정보 조회
     */
    @GetMapping("/api/trade/chat/{chatRoomId}")
    fun getTradeSuggest(@PathVariable chatRoomId: String): ResponseEntity<ChatRoomDetailResponse> {
        val response = service.getTradeSuggest(chatRoomId = chatRoomId)

        return ResponseEntity.ok(response)
    }

    /**
     * 채팅방 > 이전 채팅 목록 조회
     */
    @GetMapping("/api/me/trade/chat/{chatRoomId}/messages")
    fun getChatMessageList(@PathVariable chatRoomId: String): ResponseEntity<List<ChatMessageResponse>> {
        val response = service.getChatMessageList(chatRoomId = chatRoomId)

        return ResponseEntity.ok(response)
    }

    /**
     * 채팅방 > 메시지 전송
     */
    @MessageMapping("/chat/{chatRoomId}/message")
    @SendTo("/sub/")
    fun sendMessage(
        @DestinationVariable chatRoomId: String,
        request: ChatMessageRequest,
        authentication: Authentication
    ) {
        val userAuth = authentication.principal as UserAuth

        service.sendMessage(senderId = userAuth.userId, chatRoomId = chatRoomId, request = request)
    }

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