package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class TradeUserCompliment(
    val id: Long?,
    val complimentUserId: String,
    val complimentedUserId: String,
    val complimentAt: ZonedDateTime
)