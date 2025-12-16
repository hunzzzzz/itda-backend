package com.moira.itda.global.entity

import java.time.ZonedDateTime

data class ImageFile(
    val id: Long?,
    val fileId: String,
    val identifier: String,
    val originalFileName: String,
    val storedFileName: String,
    val size: Long,
    val fileUrl: String,
    val createdAt: ZonedDateTime
)