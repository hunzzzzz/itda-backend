package com.moira.itda.domain.gacha.dto.response

import java.time.ZonedDateTime

data class GachaResponse(
    val gachaId: String,
    val status: String,
    val title: String,
    val manufacturer: String?,
    val fileId: String,
    val fileUrl: String,
    val price: Int,
    val viewCount: Int,
    val wishCount: Int,
    val totalPickCount: Int?, // 가챠 목록 조회 시에는 사용되지 않는 필드 (null)
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
