package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class GachaItem(
    val id: Long?,
    val gachaId: String,
    val name: String,
    val rarity: String?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)