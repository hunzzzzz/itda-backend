package com.moira.itda.domain.user_temp.mytrade.dto

import java.time.ZonedDateTime

data class MyTradeItemResponse(
    val id: String,
    val tradeId: String,
    val gachaId: String,
    val type: String,
    val status: String,
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