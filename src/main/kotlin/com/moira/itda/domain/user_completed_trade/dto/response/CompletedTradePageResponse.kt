package com.moira.itda.domain.user_completed_trade.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class CompletedTradePageResponse(
    val content: List<CompletedTradeResponse>,
    val page: PageResponse
)
