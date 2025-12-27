package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class UserSignupIdentifyCode(
    val id: Long?,
    val email: String,
    val code: String,
    val expiredAt: ZonedDateTime
) {
    companion object {
        fun from(email: String, code: String): UserSignupIdentifyCode {
            return UserSignupIdentifyCode(
                id = null,
                email = email,
                code = code,
                expiredAt = ZonedDateTime.now().plusMinutes(5)
            )
        }
    }
}