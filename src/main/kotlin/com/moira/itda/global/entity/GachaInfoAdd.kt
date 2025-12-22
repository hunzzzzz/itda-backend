package com.moira.itda.global.entity

import java.time.ZonedDateTime

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
)
