package com.moira.itda.global.entity

import com.moira.itda.domain.chat.send.dto.request.MessageRequest
import java.time.ZonedDateTime

data class ChatMessage(
    val id: Long?,
    val chatRoomId: String,
    val senderId: String,
    val message: String,
    val createdAt: ZonedDateTime
) {
    companion object {
        fun fromChatMessageRequest(senderId: String, chatRoomId: String, request: MessageRequest): ChatMessage {
            return ChatMessage(
                id = null,
                chatRoomId = chatRoomId,
                senderId = senderId,
                message = request.message,
                createdAt = ZonedDateTime.now()
            )
        }
    }
}