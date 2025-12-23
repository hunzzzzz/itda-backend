package com.moira.itda.domain.user.mytradesuggest.dto.response

import java.time.ZonedDateTime

data class ExchangeSuggestResponse(
    val suggestId: String,
    val tradeId: String,
    val gachaId: String,
    val status: String,
    val sellerItemId: Long,
    val sellerItemName: String,
    val changeYn: String,
    val suggestedItemId: Long?,
    val suggestedItemName: String?,
    val content: String?,
    val createdAt: ZonedDateTime
)
