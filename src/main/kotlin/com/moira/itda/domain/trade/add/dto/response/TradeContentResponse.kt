package com.moira.itda.domain.trade.add.dto.response

data class TradeContentResponse(
    val trade: com.moira.itda.domain.trade.add.dto.response.TradeResponse,
    val items: List<com.moira.itda.domain.trade.add.dto.response.TradeItemResponse>
)
