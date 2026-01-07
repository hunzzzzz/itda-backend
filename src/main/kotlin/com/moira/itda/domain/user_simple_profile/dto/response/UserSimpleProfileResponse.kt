package com.moira.itda.domain.user_simple_profile.dto.response

data class UserSimpleProfileResponse(
    val userId: String,
    val status: String,
    val nickname: String,
    val fileId: String?,
    val fileUrl: String?,
    val createdAt: String,
    val completedTradeCount: Int,
    val banCount: Int
)
