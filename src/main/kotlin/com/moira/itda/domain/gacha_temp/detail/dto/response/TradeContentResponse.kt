package com.moira.itda.domain.gacha_temp.detail.dto.response

data class TradeContentResponse(
    val trade: TradeResponse,
    val itemList: List<TradeItemResponse>
)
