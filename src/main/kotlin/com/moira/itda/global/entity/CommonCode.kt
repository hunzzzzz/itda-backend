package com.moira.itda.global.entity

import com.moira.itda.domain.admin.code.dto.request.AdminCodeAddRequest
import java.time.ZonedDateTime

data class CommonCode(
    val codeKey: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    companion object {
        fun fromCommonCodeAddRequest(request: AdminCodeAddRequest): CommonCode {
            return CommonCode(
                codeKey = request.key,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )
        }
    }
}
