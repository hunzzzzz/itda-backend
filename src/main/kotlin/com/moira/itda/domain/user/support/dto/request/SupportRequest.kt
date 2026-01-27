package com.moira.itda.domain.user.support.dto.request

data class SupportRequest(
    val type: String,
    val content: String,
    val fileId: String?
)
