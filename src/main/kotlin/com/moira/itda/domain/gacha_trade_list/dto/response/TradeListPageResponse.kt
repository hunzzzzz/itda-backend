package com.moira.itda.domain.gacha_trade_list.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class TradeListPageResponse(
    val content: List<TradeListContentResponse>,
    val page: PageResponse
)
