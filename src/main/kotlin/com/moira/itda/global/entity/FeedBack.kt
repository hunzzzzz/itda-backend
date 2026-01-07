package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class FeedBack(
    val userId: String,
    val type: FeedBackType,
    val content: String,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
    val answerContent: String?,
    val answeredAt: ZonedDateTime?
)