package com.moira.itda.domain.suggest.dto.request

data class ExchangeSuggestRequest(
    val gachaId: String,
    val sellerItemId: Long,
    val changeYn: String,
    val originalItemId: Long,
    val suggestedItemId: Long,
    val content: String?
)
