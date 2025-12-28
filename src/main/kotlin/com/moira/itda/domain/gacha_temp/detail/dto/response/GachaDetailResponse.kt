package com.moira.itda.domain.gacha_temp.detail.dto.response

data class GachaDetailResponse(
    val gacha: GachaResponse,
    val items: List<GachaItemResponse>
)
