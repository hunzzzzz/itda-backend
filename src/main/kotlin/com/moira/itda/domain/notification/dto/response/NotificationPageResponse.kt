package com.moira.itda.domain.notification.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class NotificationPageResponse(
    val content: List<NotificationResponse>,
    val page: PageResponse
)
