package com.moira.itda.domain.user.support.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class SupportPageResponse(
    val content: List<SupportResponse>,
    val page: PageResponse
)
