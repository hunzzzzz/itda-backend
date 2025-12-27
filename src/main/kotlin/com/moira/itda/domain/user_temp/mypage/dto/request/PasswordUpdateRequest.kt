package com.moira.itda.domain.user_temp.mypage.dto.request

data class PasswordUpdateRequest(
    val oldPassword: String,
    val newPassword: String
)
