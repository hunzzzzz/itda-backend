package com.moira.itda.domain.chat.history.dto.response

import java.time.ZonedDateTime

data class ChatMessageResponse(
    val chatMessageId: Long,
    val senderId: String,
    val senderNickname: String,
    val message: String,
    val createdAt: ZonedDateTime
)