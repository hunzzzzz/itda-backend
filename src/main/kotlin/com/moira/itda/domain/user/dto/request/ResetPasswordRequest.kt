package com.moira.itda.domain.user.dto.request

data class ResetPasswordRequest(
    val email: String,
    val newPassword: String
)
