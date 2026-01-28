package com.moira.itda.global.entity

import com.moira.itda.domain.admin.gacha.dto.request.AdminGachaItemAddRequest
import java.time.ZonedDateTime

data class GachaItem(
    val id: Long?,
    val gachaId: String,
    val name: String,
    val fileId: String?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    companion object {
        fun from(gachaId: String, request: AdminGachaItemAddRequest): GachaItem {
            return GachaItem(
                id = null,
                gachaId = gachaId,
                name = request.name,
                fileId = null, // TODO: 추후 가챠 아이템에도 이미지 파일 저장
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )
        }
    }
}