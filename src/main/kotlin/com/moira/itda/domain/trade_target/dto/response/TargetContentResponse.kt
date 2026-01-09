package com.moira.itda.domain.trade_target.dto.response

data class TargetContentResponse(
    val gacha: TargetGachaResponse,
    val items: List<TargetGachaItemResponse>
)
