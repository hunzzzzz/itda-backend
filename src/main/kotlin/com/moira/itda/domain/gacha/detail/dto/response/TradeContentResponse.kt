package com.moira.itda.domain.gacha.detail.dto.response

data class TradeContentResponse(
    val trade: TradeResponse,
    val itemList: List<TradeItemResponse>
)
