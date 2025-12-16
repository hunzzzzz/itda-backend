package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class UserLoginHistory(
    val id: Long?,
    val userId: String,
    val successYn: String,
    val ipAddress: String,
    val userAgent: String,
    val loginAt: ZonedDateTime
)