package com.moira.itda.domain.account.login.dto.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)