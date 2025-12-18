package com.moira.itda.domain.target.dto.response

data class TargetContentResponse(
    val gacha: TargetGachaResponse,
    val items: List<TargetGachaItemResponse>
)
