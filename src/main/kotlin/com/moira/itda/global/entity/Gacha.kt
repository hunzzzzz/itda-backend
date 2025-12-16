package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class Gacha(
    val id: String,
    val status: String,
    val title: String,
    val manufacturer: String?,
    val fileId: String,
    val price: Int,
    val viewCount: Int,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)