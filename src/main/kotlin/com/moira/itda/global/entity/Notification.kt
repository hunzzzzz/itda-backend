package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class Notification(
    val id: Long?,
    val receiverId: String,
    val senderId: String,
    val type: NotificationType,
    val content: String,
    val targetId: String?,
    val readYn: String,
    val createdAt: ZonedDateTime
) {
    companion object {
        fun from(
            receiverId: String,
            senderId: String,
            type: NotificationType,
            content: String,
            targetId: String?
        ): Notification {
            return Notification(
                id = null,
                receiverId = receiverId,
                senderId = senderId,
                type = type,
                content = content,
                targetId = targetId,
                readYn = "N",
                createdAt = ZonedDateTime.now()
            )
        }
    }
}