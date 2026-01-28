package com.moira.itda.domain.trade.add.dto.response

import com.moira.itda.global.entity.TradeHopeMethod
import java.time.ZonedDateTime

data class TradeResponse(
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
    val hopeLocation: String?,
    val hopeAddress: String?,
    val hopeLocationLatitude: String?,
    val hopeLocationLongitude: String?,
    val suggestCount: Int,
    val createdAt: ZonedDateTime
)
