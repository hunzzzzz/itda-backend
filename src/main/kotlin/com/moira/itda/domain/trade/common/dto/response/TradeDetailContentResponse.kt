package com.moira.itda.domain.trade.common.dto.response

import com.moira.itda.domain.trade.list.dto.response.TradeItemResponse

data class TradeDetailContentResponse(
    val trade: TradeDetailResponse,
    val itemList: List<TradeItemResponse>
)
