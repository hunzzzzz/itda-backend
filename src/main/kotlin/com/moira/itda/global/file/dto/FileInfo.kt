package com.moira.itda.global.file.dto

data class FileInfo(
    val fileId: String,
    val originalFileName: String,
    val storedFileName: String,
    val size: Long,
    val fileUrl: String
)
