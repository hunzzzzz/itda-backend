package com.moira.itda.domain.gacha.dto.response

data class TargetContentResponse(
    val gacha: TargetGachaResponse,
    val items: List<TargetGachaItemResponse>
)
