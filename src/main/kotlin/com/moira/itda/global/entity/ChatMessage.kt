package com.moira.itda.global.entity

import com.moira.itda.domain.user.mychat.dto.request.ChatMessageRequest
import java.time.ZonedDateTime

data class ChatMessage(
    val id: Long?,
    val chatRoomId: String,
    val senderId: String,
    val message: String,
    val createdAt: ZonedDateTime
) {
    companion object {
        fun fromChatMessageRequest(chatRoomId: String, request: ChatMessageRequest): ChatMessage {
            return ChatMessage(
                id = null,
                chatRoomId = chatRoomId,
                senderId = request.senderId,
                message = request.message,
                createdAt = ZonedDateTime.now()
            )
        }
    }
}