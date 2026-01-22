package com.moira.itda.domain.user_reset_password.dto.request

data class ResetPasswordRequest(
    val phoneNumber: String,
    val name: String,
    val ci: String,
    val newPassword: String
)