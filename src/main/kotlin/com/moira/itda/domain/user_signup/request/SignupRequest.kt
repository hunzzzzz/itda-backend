package com.moira.itda.domain.user_signup.request

data class SignupRequest(
    val email: String,
    val password: String,
    val name: String,
    val nickname: String
)
