package com.moira.itda.domain.gacha.detail.dto.response

import com.moira.itda.global.entity.TradeItemType
import java.time.ZonedDateTime

data class TradeItemResponse(
    val id: String,
    val tradeId: String,
    val gachaId: String,
    val type: TradeItemType,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    // 구매 관련
    val purchaseItemId: Long?,
    val purchaseItemName: String?,
    val purchaseCount: Int?,
    val purchaseCurrentCount: Int?,
    val purchasePrice: Int?,
    // 교환 관련
    val exchangeGiveItemId: Long?,
    val exchangeGiveItemName: String?,
    val exchangeWantItemId: Long?,
    val exchangeWantItemName: String?
)