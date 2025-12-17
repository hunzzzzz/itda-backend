package com.moira.itda.global.entity

import com.moira.itda.domain.admin.code.dto.request.AdminCodeDetailAddRequest
import java.time.ZonedDateTime

data class CommonCodeDetail(
    val id: Long?,
    val codeKey: String,
    val engName: String,
    val korName: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    companion object {
        fun fromCommonCodeDetailAddRequest(codeKey: String, request: AdminCodeDetailAddRequest): CommonCodeDetail {
            return CommonCodeDetail(
                id = null,
                codeKey = codeKey,
                engName = request.engName,
                korName = request.korName,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )
        }
    }
}
