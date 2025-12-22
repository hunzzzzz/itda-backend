package com.moira.itda.domain.admin.info.dto.response

import java.time.ZonedDateTime

data class AdminGachaInfoAddDetailResponse(
    // 등록요청 관련
    val id: String,
    val status: String,
    val type: String,
    val content: String,
    val fileId: String,
    val fileUrl: String,
    val adminComment: String?,
    val receiveEmailYn: String,
    val requestedAt: ZonedDateTime,
    val processedAt: ZonedDateTime?,

    // 유저(요청자) 관련
    val userId: String,
    val userNickname: String,
)
