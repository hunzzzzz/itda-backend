package com.moira.itda.domain.login.dto.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)