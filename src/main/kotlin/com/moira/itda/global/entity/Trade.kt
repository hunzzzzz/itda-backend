package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class Trade(
    val id: String,
    val gachaId: String,
    val userId: String,
    val type: TradeType,
    val status: TradeStatus,
    val title: String,
    val content: String,
    val fileId: String,
    val hopeMethod: TradeHopeMethod,
    val hopeLocation: String?,
    val hopeAddress: String?,
    val hopeLocationLatitude: String?,
    val hopeLocationLongitude: String?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)