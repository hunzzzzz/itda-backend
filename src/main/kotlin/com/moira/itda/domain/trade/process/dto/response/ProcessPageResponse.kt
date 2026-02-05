package com.moira.itda.domain.trade.process.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class ProcessPageResponse(
    val content: List<ProcessResponse>,
    val page: PageResponse
)
