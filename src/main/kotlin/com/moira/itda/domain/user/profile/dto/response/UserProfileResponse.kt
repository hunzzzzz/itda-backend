package com.moira.itda.domain.user.profile.dto.response

data class UserProfileResponse(
    val userId: String,
    val status: String,
    val nickname: String,
    val fileId: String?,
    val fileUrl: String?,
    val createdAt: String,
    val completedTradeCount: Int,
    val complimentCount: Int,
    val banCount: Int
)
