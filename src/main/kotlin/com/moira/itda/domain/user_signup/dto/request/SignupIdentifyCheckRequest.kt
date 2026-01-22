package com.moira.itda.domain.user_signup.dto.request

data class SignupIdentifyCheckRequest(
    val phoneNumber: String,
    val code: String
)
