package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class UserPlace(
    val id: String,
    val userId: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val default: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
