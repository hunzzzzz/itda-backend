package com.moira.itda.domain.gacha.dto.response

data class GachaDetailResponse(
    val gacha: GachaResponse,
    val items: List<GachaItemResponse>
)