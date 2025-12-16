package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class User(
    val id: String,
    val role: UserRole,
    val status: UserStatus,
    val phone: String,
    val password: String,
    val name: String,
    val nickname: String,
    val imageUrl: String?,
    val refreshToken: String?,
    val lastLoginAt: ZonedDateTime?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)