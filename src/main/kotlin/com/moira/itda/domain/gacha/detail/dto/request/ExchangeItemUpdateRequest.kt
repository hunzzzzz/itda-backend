package com.moira.itda.domain.gacha.detail.dto.request

data class ExchangeItemUpdateRequest(
    val giveItemId: Long,
    val wantItemId: Long
)
