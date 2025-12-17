package com.moira.itda.domain.admin.code.dto.response

import java.time.ZonedDateTime

data class AdminCodeResponse(
    val codeKey: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
