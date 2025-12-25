package com.moira.itda.domain.user.mychat.dto.request

data class ChatMessageRequest(
    val senderId: String,
    val message: String
)
