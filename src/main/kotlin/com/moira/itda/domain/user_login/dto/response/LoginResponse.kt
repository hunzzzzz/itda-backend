package com.moira.itda.domain.user_login.dto.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)