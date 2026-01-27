package com.moira.itda.domain.account.reset.dto.request

data class ResetPasswordRequest(
    val phoneNumber: String,
    val name: String,
    val ci: String,
    val newPassword: String
)