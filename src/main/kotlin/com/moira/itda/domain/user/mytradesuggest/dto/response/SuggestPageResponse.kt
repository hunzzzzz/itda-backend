package com.moira.itda.domain.user.mytradesuggest.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class SuggestPageResponse(
    val content: List<*>,
    val page: PageResponse
)
