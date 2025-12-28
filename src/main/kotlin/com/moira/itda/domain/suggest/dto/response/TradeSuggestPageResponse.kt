package com.moira.itda.domain.suggest.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class TradeSuggestPageResponse(
    val content: List<TradeSuggestResponse>,
    val page: PageResponse
)
