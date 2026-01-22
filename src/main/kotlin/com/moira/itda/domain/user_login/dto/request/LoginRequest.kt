package com.moira.itda.domain.user_login.dto.request

data class LoginRequest(
    val email: String,
    val password: String
)