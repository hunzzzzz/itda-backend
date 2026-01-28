package com.moira.itda.domain.trade.list.dto.response

data class TradeListContentResponse(
    val trade: TradeListResponse,
    val items: List<TradeItemResponse>
)
