package com.moira.itda.domain.gacha.list.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class GachaListPageResponse(
    val content: List<GachaListResponse>,
    val page: PageResponse
)