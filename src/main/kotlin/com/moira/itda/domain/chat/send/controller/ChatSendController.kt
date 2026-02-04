package com.moira.itda.domain.chat.send.controller

import com.moira.itda.domain.chat.send.dto.request.MessageRequest
import com.moira.itda.domain.chat.send.service.ChatSendService
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatSendController(
    private val service: ChatSendService
) {
    /**
     * 메시지 전송
     */
    @MessageMapping("/chat/{chatRoomId}/message")
    @SendTo("/sub/")
    fun sendMessage(
        @DestinationVariable chatRoomId: String,
        request: MessageRequest,
        authentication: Authentication
    ) {
        val userAuth = authentication.principal as UserAuth

        service.sendMessage(senderId = userAuth.userId, chatRoomId = chatRoomId, request = request)
    }
}