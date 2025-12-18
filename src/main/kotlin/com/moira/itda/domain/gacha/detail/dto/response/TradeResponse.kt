package com.moira.itda.domain.gacha.detail.dto.response

import java.time.ZonedDateTime

data class TradeResponse(
    val id: String,
    val type: String,
    val status: String,
    val title: String,
    val fileId: String,
    val fileUrl: String,
    val hopeMethod: String,
    val hopeAddress: String?,
    val createdAt: ZonedDateTime,

    val userId: String,
    val userNickname: String
)
