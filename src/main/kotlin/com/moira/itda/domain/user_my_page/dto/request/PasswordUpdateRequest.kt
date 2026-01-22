package com.moira.itda.domain.user_my_page.dto.request

data class PasswordUpdateRequest(
    val oldPassword: String,
    val newPassword: String
)