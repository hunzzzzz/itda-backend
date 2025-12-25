package com.moira.itda.domain.chat.controller

import com.moira.itda.domain.chat.dto.request.ChatMessageRequest
import com.moira.itda.domain.chat.service.ChatService
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 채팅방
 */
@RestController
class ChatController(
    private val chatService: ChatService
) {
    /**
     * 채팅방 > 메시지 전송
     */
    @MessageMapping("/chat/{chatRoomId}/message")
    fun sendMessage(
        @DestinationVariable chatRoomId: String,
        request: ChatMessageRequest
    ): ResponseEntity<Nothing> {
        chatService.sendMessage(chatRoomId = chatRoomId, request = request)

        return ResponseEntity.ok(null)
    }
}