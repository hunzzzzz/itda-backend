package com.moira.itda.domain.admin.user.dto.request

data class UserBanRequest(
    val reason: String,
    val banDays: Int
)
