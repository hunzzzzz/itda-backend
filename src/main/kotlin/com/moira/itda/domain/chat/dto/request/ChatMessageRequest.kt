package com.moira.itda.domain.chat.dto.request

data class ChatMessageRequest(
    val senderId: String,
    val message: String
)
