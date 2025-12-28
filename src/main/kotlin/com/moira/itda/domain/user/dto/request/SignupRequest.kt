package com.moira.itda.domain.user.dto.request

data class SignupRequest(
    val email: String,
    val password: String,
    val name: String,
    val nickname: String
)
