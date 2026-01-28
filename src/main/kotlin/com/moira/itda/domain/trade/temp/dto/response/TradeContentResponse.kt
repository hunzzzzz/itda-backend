package com.moira.itda.domain.trade.temp.dto.response

data class TradeContentResponse(
    val trade: com.moira.itda.domain.trade.temp.dto.response.TradeResponse,
    val items: List<com.moira.itda.domain.trade.temp.dto.response.TradeItemResponse>
)
