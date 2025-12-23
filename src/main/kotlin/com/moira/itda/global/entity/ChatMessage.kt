package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class ChatMessage(
    val id: Long?,
    val chatRoomId: String,
    val senderId: String,
    val message: String,
    val createdAt: ZonedDateTime
)