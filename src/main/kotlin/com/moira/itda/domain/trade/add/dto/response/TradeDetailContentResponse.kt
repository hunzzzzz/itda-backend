package com.moira.itda.domain.trade.add.dto.response

data class TradeDetailContentResponse(
    val trade: com.moira.itda.domain.trade.add.dto.response.TradeDetailResponse,
    val itemList: List<com.moira.itda.domain.trade.add.dto.response.TradeItemResponse>
)
