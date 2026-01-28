package com.moira.itda.domain.trade.temp.dto.response

data class TradeDetailContentResponse(
    val trade: com.moira.itda.domain.trade.temp.dto.response.TradeDetailResponse,
    val itemList: List<com.moira.itda.domain.trade.temp.dto.response.TradeItemResponse>
)
