package com.moira.itda.domain.user.signup.dto.request

data class SignupRequest(
    val email: String,
    val password: String,
    val name: String,
    val nickname: String
)
