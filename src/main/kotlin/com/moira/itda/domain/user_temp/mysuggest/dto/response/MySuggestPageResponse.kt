package com.moira.itda.domain.user_temp.mysuggest.dto.response

import com.moira.itda.domain.suggest.dto.response.MyTradeSuggestResponse
import com.moira.itda.global.pagination.dto.PageResponse

data class MySuggestPageResponse(
    val suggest: List<MyTradeSuggestResponse>,
    val page: PageResponse
)
