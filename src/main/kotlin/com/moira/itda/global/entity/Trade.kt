package com.moira.itda.global.entity

import com.moira.itda.domain.trade.dto.request.TradeAddRequest
import java.time.ZonedDateTime
import java.util.*

data class Trade(
    val id: String,
    val gachaId: String,
    val userId: String,
    val type: TradeType,
    val status: TradeStatus,
    val title: String,
    val content: String,
    val fileId: String,
    val hopeMethod: TradeHopeMethod,
    val hopeLocation: String?,
    val hopeAddress: String?,
    val hopeLocationLatitude: String?,
    val hopeLocationLongitude: String?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
) {
    companion object {
        fun fromRequest(type: TradeType, userId: String, gachaId: String, request: TradeAddRequest): Trade {
            return Trade(
                id = UUID.randomUUID().toString(),
                gachaId = gachaId,
                userId = userId,
                type = type,
                status = TradeStatus.PENDING,
                title = request.title,
                content = request.content,
                fileId = request.fileId,
                hopeMethod = TradeHopeMethod.valueOf(request.hopeMethod),
                hopeLocation = request.hopeLocation,
                hopeAddress = request.hopeAddress,
                hopeLocationLatitude = request.hopeLocationLatitude,
                hopeLocationLongitude = request.hopeLocationLongitude,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )
        }
    }
}