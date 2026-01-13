package com.moira.itda.domain.chat.controller

import com.moira.itda.domain.chat.dto.request.ChatMessageRequest
import com.moira.itda.domain.chat.dto.request.TradeCompleteRequest
import com.moira.itda.domain.chat.dto.response.ChatMessageResponse
import com.moira.itda.domain.chat.dto.response.ChatRoomResponse
import com.moira.itda.domain.chat.dto.response.MyChatPageResponse
import com.moira.itda.domain.chat.service.ChatService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
class ChatController(
    private val chatService: ChatService
) {
    /**
     * 내 활동 > 채팅 > 채팅방 목록 조회
     */
    @GetMapping("/api/me/trade/chat")
    fun getChatList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<MyChatPageResponse?> {
        val response = chatService.getChatList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }

    /**
     * 내 활동 > 채팅 > 채팅방 > 거래제안 정보 조회
     */
    @GetMapping("/api/me/trade/chat/{chatRoomId}")
    fun getTradeSuggest(@PathVariable chatRoomId: String): ResponseEntity<ChatRoomResponse> {
        val response = chatService.getTradeSuggest(chatRoomId = chatRoomId)

        return ResponseEntity.ok(response)
    }

    /**
     * 내 활동 > 채팅 > 채팅방 > 이전 채팅 목록 조회
     */
    @GetMapping("/api/me/trade/chat/{chatRoomId}/messages")
    fun getChatMessageList(@PathVariable chatRoomId: String): ResponseEntity<List<ChatMessageResponse>> {
        val response = chatService.getChatMessageList(chatRoomId = chatRoomId)

        return ResponseEntity.ok(response)
    }

    /**
     * 내 활동 > 채팅 > 채팅방 > 메시지 전송
     */
    @MessageMapping("/chat/{chatRoomId}/message")
    @SendTo("/sub/")
    fun sendMessage(
        @DestinationVariable chatRoomId: String,
        request: ChatMessageRequest,
        authentication: Authentication
    ) {
        val userAuth = authentication.principal as UserAuth

        chatService.sendMessage(senderId = userAuth.userId, chatRoomId = chatRoomId, request = request)
    }

    /**
     * 내 활동 > 채팅 > 채팅방 > 거래완료
     */
    @PutMapping("/api/me/trade/chat/{chatRoomId}/complete")
    fun completeTrade(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable chatRoomId: String,
        @RequestBody request: TradeCompleteRequest
    ): ResponseEntity<Nothing> {
        chatService.completeTrade(userId = userAuth.userId, chatRoomId = chatRoomId, request = request)

        return ResponseEntity.ok(null)
    }
}