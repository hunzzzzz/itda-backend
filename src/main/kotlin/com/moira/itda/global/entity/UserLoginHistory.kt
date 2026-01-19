package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class UserLoginHistory(
    val id: Long?,
    val userId: String,
    val successYn: String,
    val ipAddress: String,
    val userAgent: String,
    val failReason: UserLoginFailReason?,
    val loginAt: ZonedDateTime
) {
    companion object {
        fun from(
            userId: String,
            successYn: String,
            ipAddress: String,
            userAgent: String,
            failReason: UserLoginFailReason?
        ): UserLoginHistory {
            return UserLoginHistory(
                id = null,
                userId = userId,
                successYn = successYn,
                ipAddress = ipAddress,
                userAgent = userAgent,
                failReason = failReason,
                loginAt = ZonedDateTime.now()
            )
        }
    }
}