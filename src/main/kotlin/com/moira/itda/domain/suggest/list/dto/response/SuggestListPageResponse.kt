package com.moira.itda.domain.suggest.list.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class SuggestListPageResponse(
    val content: List<SuggestListResponse>,
    val page: PageResponse
)
