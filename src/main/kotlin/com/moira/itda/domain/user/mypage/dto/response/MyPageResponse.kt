package com.moira.itda.domain.user.mypage.dto.response

import java.time.ZonedDateTime

data class MyPageResponse(
    val userId: String,
    val role: String,
    val status: String,
    val email: String,
    val name: String,
    val nickname: String,
    val imageUrl: String?,
    val lastLoginAt: ZonedDateTime?,
    val createdAt: ZonedDateTime
)
