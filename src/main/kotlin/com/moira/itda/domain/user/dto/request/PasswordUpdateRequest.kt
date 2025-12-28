package com.moira.itda.domain.user.dto.request

data class PasswordUpdateRequest(
    val oldPassword: String,
    val newPassword: String
)