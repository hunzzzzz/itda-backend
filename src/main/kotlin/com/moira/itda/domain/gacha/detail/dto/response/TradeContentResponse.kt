package com.moira.itda.domain.gacha.detail.dto.response

data class TradeContentResponse(
    val trade: TradeResponse,
    val salesItemList: List<TradeSalesItemResponse>,
    val exchangeItemList: List<Any> // TODO
)
