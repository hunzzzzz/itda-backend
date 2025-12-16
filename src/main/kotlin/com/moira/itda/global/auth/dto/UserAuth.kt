package com.moira.itda.global.auth.dto

data class UserAuth(
    val userId: String,
    val email: String,
    val nickname: String,
    val role: String
)
