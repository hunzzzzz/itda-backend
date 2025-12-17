package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class GachaWish(
    val id: Long?,
    val userId: String,
    val gachaId: String,
    val createdAt: ZonedDateTime
)