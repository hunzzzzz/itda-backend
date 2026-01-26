package com.moira.itda.domain.suggest_list.dto.request

data class SuggestYnRequest(
    val tradeItemId: String,
    val tradeSuggestId: String,
    val userId: String
)