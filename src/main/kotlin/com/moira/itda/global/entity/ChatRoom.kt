package com.moira.itda.global.entity

import java.time.ZonedDateTime
import java.util.UUID

data class ChatRoom(
    val id: UUID,
    val tradeId: String,
    val sellerId: String,
    val buyerId: String,
    val status: ChatStatus,
    val createdAt: ZonedDateTime
)