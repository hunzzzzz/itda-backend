package com.moira.itda.domain.user_gacha_wish_list.dto.response

import com.moira.itda.global.pagination.dto.PageResponse

data class WishGachaPageResponse(
    val content: List<WishGachaResponse>,
    val page: PageResponse
)