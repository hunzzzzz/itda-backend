package com.moira.itda.domain.user.mysuggest.dto.response

import java.time.ZonedDateTime

data class MySuggestResponse(
    // 거래 정보
    val tradeId: String,
    val tradeStatus: String,
    val userId: String,
    val userNickname: String,
    val fileId: String,
    val fileUrl: String,
    val cratedAt: String,
    // 가챠 정보
    val gachaId: String,
    val gachaItemId: Long,
    val gachaItemName: String,
    // 제안 정보
    val suggestId: String,
    val suggestStatus: String,
    val content: String?,
    val count: Int,
    val discountYn: String,
    val discountPrice: Int?,
    val createdAt: ZonedDateTime
)
