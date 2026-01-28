package com.moira.itda.domain.trade.add.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class TradePageResponse(
    val content: List<com.moira.itda.domain.trade.add.dto.response.TradeContentResponse>,
    val page: PageResponse
)
