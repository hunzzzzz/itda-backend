package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class UserIdentifyCode(
    val id: Long?,
    val phoneNumber: String,
    val code: String,
    val type: UserIdentifyCodeType,
    val name: String?,
    val ci: String?,
    val expiredAt: ZonedDateTime
) {
    companion object {
        fun from(phoneNumber: String, code: String, type: UserIdentifyCodeType): UserIdentifyCode {
            return UserIdentifyCode(
                id = null,
                phoneNumber = phoneNumber,
                code = code,
                type = type,
                name = null,
                ci = null,
                expiredAt = ZonedDateTime.now().plusMinutes(5)
            )
        }
    }
}