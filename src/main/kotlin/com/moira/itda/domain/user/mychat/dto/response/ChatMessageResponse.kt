package com.moira.itda.domain.user.mychat.dto.response

import java.time.ZonedDateTime

data class ChatMessageResponse(
    val chatMessageId: Long,
    val senderId: String,
    val senderNickname: String,
    val message: String,
    val createdAt: ZonedDateTime
)
