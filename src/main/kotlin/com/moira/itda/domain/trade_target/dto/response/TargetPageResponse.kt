package com.moira.itda.domain.trade_target.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class TargetPageResponse(
    val content: List<TargetContentResponse>,
    val page: PageResponse
)
