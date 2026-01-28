package com.moira.itda.domain.trade.list.dto.response

import com.moira.itda.global.entity.TradeHopeMethod
import java.time.ZonedDateTime

data class TradeListResponse(
    val tradeId: String,
    val gachaId: String,
    val userId: String,
    val userNickname: String,
    val type: String,
    val status: String,
    val title: String,
    val content: String?,
    val fileId: String,
    val fileUrl: String,
    val hopeMethod: TradeHopeMethod,
    val hopeDirectTradeLocation: String?,
    val suggestCount: Int,
    val createdAt: ZonedDateTime
)
