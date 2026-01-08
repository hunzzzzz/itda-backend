package com.moira.itda.domain.gacha.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class GachaPageResponse(
    val content: List<GachaResponse>,
    val page: PageResponse
)