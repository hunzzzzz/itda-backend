package com.moira.itda.domain.user_my_page.dto.response

import java.time.ZonedDateTime

data class MyPageResponse(
    val userId: String,
    val role: String,
    val status: String,
    val email: String,
    val nickname: String,
    val phoneNumber: String,
    val fileId: String?,
    val imageFileUrl: String?,
    val tradeCount: Int,
    val complimentCount: Int,
    val lastLoginAt: ZonedDateTime?,
    val createdAt: ZonedDateTime
)