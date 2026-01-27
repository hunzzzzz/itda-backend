package com.moira.itda.domain.account.signup.dto.request

data class SignupRequest(
    val email: String,
    val name: String,
    val password: String,
    val phoneNumber: String,
    val ci: String
)