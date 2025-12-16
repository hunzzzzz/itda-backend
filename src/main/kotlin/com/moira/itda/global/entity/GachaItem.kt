package com.moira.itda.global.entity

import com.moira.itda.domain.admin.series.dto.request.AdminSeriesItemAddRequest
import java.time.ZonedDateTime

data class GachaItem(
    val id: Long?,
    val seriesId: String,
    val name: String,
    val rarity: String?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    companion object {
        fun fromAdminSeriesItemAddRequest(seriesId: String, request: AdminSeriesItemAddRequest): GachaItem {
            return GachaItem(
                id = null,
                seriesId = seriesId,
                name = request.name,
                rarity = null,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )
        }
    }
}