package com.moira.itda.domain.trade.dto.response

data class TradeContentResponse(
    val trade: TradeResponse,
    val itemList: List<TradeItemResponse>
)
