package com.moira.itda.domain.notification.dto.response

import java.time.ZonedDateTime

data class NotificationResponse(
    val notificationId: Long,
    val senderId: String,
    val senderNickname: String,
    val type: String,
    val content: String,
    val targetId: String,
    val readYn: String,
    val createdAt: ZonedDateTime
)
