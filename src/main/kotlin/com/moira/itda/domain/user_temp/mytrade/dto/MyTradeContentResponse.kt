package com.moira.itda.domain.user_temp.mytrade.dto

data class MyTradeContentResponse(
    val trade: MyTradeResponse,
    val items: List<MyTradeItemResponse>
)
