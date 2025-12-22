package com.moira.itda.global.entity

import com.moira.itda.domain.info.dto.request.GachaInfoAddRequest
import java.time.ZonedDateTime
import java.util.UUID

data class GachaInfoAdd(
    val id: String,
    val userId: String,
    val status: GachaInfoStatus,
    val type: GachaInfoType,
    val content: String,
    val fileId: String?,
    val adminComment: String?,
    val receiveEmailYn: String,
    val requestedAt: ZonedDateTime,
    val processedAt: ZonedDateTime?
) {
    companion object {
        fun fromGachaInfoAddRequest(userId: String, request: GachaInfoAddRequest): GachaInfoAdd {
            return GachaInfoAdd(
                id = UUID.randomUUID().toString(),
                userId = userId,
                status = GachaInfoStatus.PENDING,
                type = GachaInfoType.valueOf(request.type),
                content = request.content,
                fileId = request.fileId,
                adminComment = null,
                receiveEmailYn = request.receiveEmailYn,
                requestedAt = ZonedDateTime.now(),
                processedAt = null
            )
        }
    }
}