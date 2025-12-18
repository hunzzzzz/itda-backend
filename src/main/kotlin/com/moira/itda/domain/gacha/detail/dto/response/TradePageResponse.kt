package com.moira.itda.domain.gacha.detail.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class TradePageResponse(
    val content: List<TradeContentResponse>,
    val page: PageResponse
)
