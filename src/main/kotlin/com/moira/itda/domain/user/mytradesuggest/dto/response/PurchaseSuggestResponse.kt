package com.moira.itda.domain.user.mytradesuggest.dto.response

import java.time.ZonedDateTime

data class PurchaseSuggestResponse(
    val suggestId: String,
    val type: String,
    val tradeId: String,
    val gachaId: String,
    val userId: String,
    val userNickname: String,
    val gachaItemId: Long,
    val gachaItemName: String,
    val status: String,
    val content: String?,
    val count: Int,
    val discountYn: String,
    val discountPrice: Int?,
    val createdAt: ZonedDateTime
)
