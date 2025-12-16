package com.moira.itda.domain.user.mypage.dto.request

data class PasswordUpdateRequest(
    val oldPassword: String,
    val newPassword: String
)
