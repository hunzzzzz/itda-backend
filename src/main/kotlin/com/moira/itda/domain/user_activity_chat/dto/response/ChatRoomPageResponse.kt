package com.moira.itda.domain.user_activity_chat.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class ChatRoomPageResponse(
    val content: List<ChatRoomResponse>,
    val page: PageResponse
)
