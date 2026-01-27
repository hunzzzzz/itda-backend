package com.moira.itda.global.entity

import com.moira.itda.domain.user.support.dto.request.SupportRequest
import java.time.ZonedDateTime

data class UserSupport(
    val id: Long?,
    val userId: String,
    val type: UserSupportType,
    val content: String,
    val fileId: String?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    val answerContent: String?,
    val answeredAt: ZonedDateTime?
) {
    companion object {
        fun from(userId: String, request: SupportRequest): UserSupport {
            return UserSupport(
                id = null,
                userId = userId,
                type = UserSupportType.valueOf(request.type),
                content = request.content,
                fileId = request.fileId,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now(),
                answerContent = null,
                answeredAt = null
            )
        }
    }
}