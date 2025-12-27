package com.moira.itda.global.entity

import com.moira.itda.domain.gacha_add_suggest.dto.request.GachaAddSuggestRequest
import java.time.ZonedDateTime
import java.util.*

data class GachaAddSuggest(
    val id: String,
    val userId: String,
    val status: GachaInfoStatus,
    val content: String,
    val fileId: String?,
    val adminComment: String?,
    val receiveEmailYn: String,
    val requestedAt: ZonedDateTime,
    val processedAt: ZonedDateTime?
) {
    companion object {
        fun fromRequest(userId: String, request: GachaAddSuggestRequest): GachaAddSuggest {
            return GachaAddSuggest(
                id = UUID.randomUUID().toString(),
                userId = userId,
                status = GachaInfoStatus.PENDING,
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