package com.moira.itda.domain.chat.list.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class ChatRoomPageResponse(
    val content: List<ChatRoomResponse>,
    val page: PageResponse
)
