package com.moira.itda.global.entity

import com.moira.itda.global.file.dto.FileInfo
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
) {
    companion object {
        fun fromFileInfo(fileInfo: FileInfo, identifier: String): ImageFile {
            return ImageFile(
                id = null,
                fileId = fileInfo.fileId,
                identifier = identifier,
                originalFileName = fileInfo.originalFileName,
                storedFileName = fileInfo.storedFileName,
                size = fileInfo.size,
                fileUrl = fileInfo.fileUrl,
                createdAt = ZonedDateTime.now()
            )
        }
    }
}