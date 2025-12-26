package com.moira.itda.domain.user.mytrade.dto

import com.moira.itda.global.entity.TradeItemType
import java.time.ZonedDateTime

data class MyTradeItemResponse(
    val id: String,
    val tradeId: String,
    val gachaId: String,
    val type: TradeItemType,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    // 판매 관련
    val salesItemId: Long?,
    val salesItemName: String?,
    val salesCount: Int?,
    val salesCurrentCount: Int?,
    val salesPrice: Int?,
    // 교환 관련
    val exchangeGiveItemId: Long?,
    val exchangeGiveItemName: String?,
    val exchangeWantItemId: Long?,
    val exchangeWantItemName: String?
)