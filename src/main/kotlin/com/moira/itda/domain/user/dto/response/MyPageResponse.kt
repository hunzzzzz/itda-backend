package com.moira.itda.domain.user.dto.response

import java.time.ZonedDateTime

data class MyPageResponse(
    val userId: String,
    val role: String,
    val status: String,
    val email: String,
    val name: String,
    val nickname: String,
    val fileId: String?,
    val imageFileUrl: String?,
    val tradeCount: Int,
    val complimentCount: Int,
    val lastLoginAt: ZonedDateTime?,
    val createdAt: ZonedDateTime
)