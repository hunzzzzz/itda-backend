package com.moira.itda.domain.trade.dto.response

data class TradeContentResponse(
    val trade: TradeResponse,
    val items: List<TradeItemResponse>
)
