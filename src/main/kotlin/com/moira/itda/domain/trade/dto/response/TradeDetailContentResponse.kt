package com.moira.itda.domain.trade.dto.response

data class TradeDetailContentResponse(
    val trade: TradeDetailResponse,
    val itemList: List<TradeItemResponse>
)
