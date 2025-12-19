package com.moira.itda.domain.suggest.dto.request

data class SalesSuggestRequest(
    val items: List<SalesItemSuggestRequest>,
    val content: String?
)
