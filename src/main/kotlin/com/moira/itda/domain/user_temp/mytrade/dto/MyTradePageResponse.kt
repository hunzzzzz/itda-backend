package com.moira.itda.domain.user_temp.mytrade.dto

import com.moira.itda.global.pagination.dto.PageResponse

data class MyTradePageResponse(
    val content: List<MyTradeContentResponse>,
    val page: PageResponse
)
