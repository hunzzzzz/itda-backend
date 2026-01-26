package com.moira.itda.domain.admin_user.dto.response

import java.time.ZonedDateTime

data class AdminUserBanResponse(
    val id: Long,
    val reason: String,
    val bannedAt: ZonedDateTime,
    val bannedUntil: ZonedDateTime
)
