package com.moira.itda.domain.trade.list.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class TradeListPageResponse(
    val content: List<TradeListContentResponse>,
    val page: PageResponse
)
