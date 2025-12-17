package com.moira.itda.domain.admin.code.dto.response

import java.time.ZonedDateTime

data class AdminCodeDetailResponse(
    val detailId: Long,
    val engName: String,
    val korName: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
