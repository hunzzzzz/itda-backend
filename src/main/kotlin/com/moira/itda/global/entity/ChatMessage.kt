package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class ChatMessage(
    val id: Long?,
    val chatRoomId: String,
    val senderId: String,
    val message: String,
    val createdAt: ZonedDateTime
) {
    companion object {
        fun firstChat(chatRoomId: String, message: String): ChatMessage {
            return ChatMessage(
                id = null,
                chatRoomId = chatRoomId,
                senderId = "SYSTEM",
                message = message,
                createdAt = ZonedDateTime.now()
            )
        }
    }
}