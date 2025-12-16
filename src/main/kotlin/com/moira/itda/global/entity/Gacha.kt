package com.moira.itda.global.entity

import com.moira.itda.domain.admin.series.dto.request.AdminSeriesAddRequest
import java.time.ZonedDateTime

data class GachaSeries(
    val id: String,
    val status: String,
    val title: String,
    val manufacturer: String?,
    val fileId: String,
    val price: Int,
    val viewCount: Int,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    companion object {
        fun fromAdminSeriesAddRequest(seriesId: String, request: AdminSeriesAddRequest): GachaSeries {
            return GachaSeries(
                id = seriesId,
                status = GachaSeriesStatus.ACTIVE.name,
                title = request.title,
                manufacturer = request.manufacturer,
                fileId = request.fileId,
                price = request.price,
                viewCount = 0,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )
        }
    }
}