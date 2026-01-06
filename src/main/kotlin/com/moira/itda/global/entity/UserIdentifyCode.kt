package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class UserIdentifyCode(
    val id: Long?,
    val email: String,
    val code: String,
    val type: UserIdentifyCodeType,
    val expiredAt: ZonedDateTime
) {
    companion object {
        fun from(email: String, code: String, type: UserIdentifyCodeType): UserIdentifyCode {
            return UserIdentifyCode(
                id = null,
                email = email,
                code = code,
                type = type,
                expiredAt = ZonedDateTime.now().plusMinutes(5)
            )
        }
    }
}