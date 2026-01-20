package com.moira.itda.domain.signup.dto.request

data class SignupIdentifyCheckRequest(
    val phoneNumber: String,
    val code: String
)
