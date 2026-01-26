package com.moira.itda.global.entity

import com.moira.itda.domain.admin_user.dto.request.UserBanRequest
import java.time.ZonedDateTime

data class UserBanHistory(
    val id: Long?,
    val userId: String,
    val reason: String,
    val bannedAt: ZonedDateTime,
    val bannedUntil: ZonedDateTime
) {
    companion object {
        fun fromBanRequest(userId: String, request: UserBanRequest): UserBanHistory {
            return UserBanHistory(
                id = null,
                userId = userId,
                reason = request.reason,
                bannedAt = ZonedDateTime.now(),
                bannedUntil = ZonedDateTime.now().plusDays(request.banDays.toLong())
            )
        }
    }
}