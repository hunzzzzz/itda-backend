package com.moira.itda.domain.trade.temp.dto.response

import java.time.ZonedDateTime

data class TradeItemResponse(
    val tradeItemId: String,
    val tradeId: String,
    val gachaId: String,
    val type: String,
    val status: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    // 구매 관련
    val salesItemId: Long?,
    val salesItemName: String?,
    val salesPrice: Int?,
    // 교환 관련
    val exchangeGiveItemId: Long?,
    val exchangeGiveItemName: String?,
    val exchangeWantItemId: Long?,
    val exchangeWantItemName: String?
)