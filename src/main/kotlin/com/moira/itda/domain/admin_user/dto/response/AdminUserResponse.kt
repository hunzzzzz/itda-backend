package com.moira.itda.domain.admin_user.dto.response

import com.moira.itda.global.entity.UserRole
import com.moira.itda.global.entity.UserStatus
import java.time.ZonedDateTime

data class AdminUserResponse(
    val userId: String,
    val role: UserRole,
    val status: UserStatus,
    val email: String,
    val name: String,
    val nickname: String,
    val fileId: String?,
    val imageUrl: String?,
    val lastLoginAt: ZonedDateTime?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)