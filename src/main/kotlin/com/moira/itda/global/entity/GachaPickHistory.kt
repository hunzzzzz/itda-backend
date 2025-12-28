package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class GachaPickHistory(
    val id: Long?,
    val gachaId: String,
    val gachaItemId: Long,
    val userId: String,
    val createdAt: ZonedDateTime
) {
    companion object {
        fun from(gachaId: String, gachaItemId: Long, userId: String): GachaPickHistory {
            return GachaPickHistory(
                id = null,
                gachaId = gachaId,
                gachaItemId = gachaItemId,
                userId = userId,
                createdAt = ZonedDateTime.now()
            )
        }
    }
}