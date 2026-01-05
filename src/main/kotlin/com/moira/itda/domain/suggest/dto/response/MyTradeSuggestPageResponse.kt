package com.moira.itda.domain.suggest.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class MyTradeSuggestPageResponse(
    val content: List<MyTradeSuggestResponse>,
    val page: PageResponse
)
