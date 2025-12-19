package com.moira.itda.domain.user.mytrade.dto

data class MyTradeContentResponse(
    val trade: MyTradeResponse,
    val items: List<*>
)
