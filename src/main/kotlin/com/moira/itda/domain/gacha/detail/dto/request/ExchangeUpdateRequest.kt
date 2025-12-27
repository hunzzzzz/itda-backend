package com.moira.itda.domain.gacha.detail.dto.request

data class ExchangeUpdateRequest(
    val title: String,
    val content: String,
    val fileId: String,
    val hopeMethod: String,
    val hopeLocation: String?,
    val hopeAddress: String?,
    val hopeLocationLatitude: String?,
    val hopeLocationLongitude: String?,
    val items: List<ExchangeItemUpdateRequest>
)
