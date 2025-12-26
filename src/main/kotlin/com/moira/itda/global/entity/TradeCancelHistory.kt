package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class TradeCancelHistory(
    val id: Long?,
    val chatRoomId: String,
    val canceledUserId: String,
    val cancelReason: String,
    val content: String?,
    val canceledAt: ZonedDateTime
)
