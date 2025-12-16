package com.moira.itda.global.entity

import com.moira.itda.domain.admin.gacha.dto.request.AdminGachaAddRequest
import java.time.ZonedDateTime

data class Gacha(
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
        fun fromAdminGachaAddRequest(gachaId: String, request: AdminGachaAddRequest): Gacha {
            return Gacha(
                id = gachaId,
                status = GachaStatus.ACTIVE.name,
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