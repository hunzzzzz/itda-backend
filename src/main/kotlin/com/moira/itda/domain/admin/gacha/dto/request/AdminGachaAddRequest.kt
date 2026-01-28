package com.moira.itda.domain.admin.gacha.dto.request

data class AdminGachaAddRequest(
    val title: String,
    val manufacturer: String,
    val fileId: String,
    val items: List<AdminGachaItemAddRequest>
)
