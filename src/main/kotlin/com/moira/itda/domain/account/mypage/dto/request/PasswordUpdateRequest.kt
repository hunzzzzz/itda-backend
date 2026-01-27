package com.moira.itda.domain.account.mypage.dto.request

data class PasswordUpdateRequest(
    val oldPassword: String,
    val newPassword: String
)