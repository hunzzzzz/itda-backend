package com.moira.itda.domain.gacha_add_suggest.dto.request

data class GachaAddSuggestRequest(
    val content: String,
    val fileId: String?,
    val receiveEmailYn: String
)
