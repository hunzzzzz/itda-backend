package com.moira.itda.domain.info.dto.request

data class GachaInfoAddRequest(
    val type: String,
    val content: String,
    val fileId: String?,
    val receiveEmailYn: String
)
