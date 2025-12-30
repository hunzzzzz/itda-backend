package com.moira.itda.domain.trade.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class TradePageResponse(
    val content: List<TradeContentResponse>,
    val page: PageResponse
)
