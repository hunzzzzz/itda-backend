package com.moira.itda.domain.gacha_trade_list.dto.response

data class TradeListContentResponse(
    val trade: TradeListResponse,
    val items: List<TradeListItemResponse>
)
