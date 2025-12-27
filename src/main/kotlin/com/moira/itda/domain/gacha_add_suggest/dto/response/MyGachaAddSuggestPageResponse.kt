package com.moira.itda.domain.gacha_add_suggest.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class MyGachaAddSuggestPageResponse(
    val content: List<MyGachaAddSuggestResponse>,
    val page: PageResponse
)
