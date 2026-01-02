package com.moira.itda.domain.trade.dto.response

import java.time.ZonedDateTime

data class TradeDetailResponse(
    val tradeId: String,
    val gachaId: String,
    val type: String,
    val status: String,
    val title: String,
    val content: String?,
    val fileId: String,
    var fileUrlList: List<String>?,
    val hopeMethod: String,
    val hopeLocation: String?,
    val hopeAddress: String?,
    val hopeLocationLatitude: String?,
    val hopeLocationLongitude: String?,
    val createdAt: ZonedDateTime
)
