package com.moira.itda.domain.user_temp.mychat.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class MyChatPageResponse(
    val content: List<MyChatResponse>,
    val page: PageResponse
)
