package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class TradeUserReport(
    val id: Long?,
    val chatRoomId: String,
    val tradeId: String,
    val tradeSuggestId: String,
    val gachaId: String,
    val reportUserId: String,
    val reportedUserId: String,
    val reportReason: String,
    val reportAt: ZonedDateTime
)