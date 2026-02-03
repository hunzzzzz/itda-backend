package com.moira.itda.domain.suggest.approve.dto.request

data class ApproveRequest(
    val tradeId: String,
    val tradeItemId: String,
    val userId: String
)