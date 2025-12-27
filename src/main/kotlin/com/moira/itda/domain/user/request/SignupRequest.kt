package com.moira.itda.domain.user.request

data class SignupRequest(
    val email: String,
    val password: String,
    val name: String,
    val nickname: String
)
