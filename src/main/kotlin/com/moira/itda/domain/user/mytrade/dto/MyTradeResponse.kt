package com.moira.itda.domain.user.mytrade.dto

import java.time.ZonedDateTime

data class MyTradeResponse(
    val tradeId: String,
    val gachaId: String,
    val userId: String,
    val type: String,
    val status: String,
    val fileId: String,
    val fileUrl: String,
    val hopeMethod: String,
    val createdAt: ZonedDateTime
)
