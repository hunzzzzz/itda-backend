package com.moira.itda.domain.common.image.service

import com.moira.itda.domain.common.image.dto.request.CommonImageUploadRequest
import com.moira.itda.domain.common.image.dto.response.FileIdResponse
import com.moira.itda.domain.common.image.dto.response.FileUrlResponse
import com.moira.itda.domain.common.image.mapper.CommonImageMapper
import com.moira.itda.global.entity.ImageFile
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.file.component.AwsS3Handler
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class CommonImageService(
    private val awsS3Handler: AwsS3Handler,
    private val commonImageMapper: CommonImageMapper
) {
    companion object {
        private const val MAX_FILE_SIZE = 5L * 1024 * 1024 // 5MB
        private val ALLOWED_EXTENSIONS: Set<String> = setOf("jpg", "jpeg", "png", "webp")
    }

    /**
     * 이미지 업로드 > 유효성 검사
     */
    private fun validate(files: List<MultipartFile>) {
        if (files.isEmpty()) {
            throw ItdaException(ErrorCode.INVALID_FILE)
        }

        for (file in files) {
            if (file.isEmpty) {
                throw ItdaException(ErrorCode.INVALID_FILE)
            }

            if (file.size > MAX_FILE_SIZE) {
                throw ItdaException(ErrorCode.FILE_SIZE_LIMIT_EXCEEDED)
            }

            val originalFileName = file.originalFilename ?: throw ItdaException(ErrorCode.INVALID_FILE)
            val extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).lowercase()

            if (extension.isEmpty() || !ALLOWED_EXTENSIONS.contains(extension)) {
                throw ItdaException(ErrorCode.UNSUPPORTED_FILE_EXTENSION)
            }
        }
    }

    /**
     * 이미지 파일 업로드
     */
    fun uploadImage(request: CommonImageUploadRequest): FileIdResponse {
        // [1] 유효성 검사
        this.validate(files = request.files)

        // [2] 이미지 파일 저장 (AWS S3)
        val fileId = UUID.randomUUID().toString()

        request.files.forEachIndexed { index, file ->
            val compressedBytes = awsS3Handler.compressImage(file)

            val fileInfo = awsS3Handler.upload(
                fileBytes = compressedBytes,
                originalFileName = file.originalFilename ?: "image.webp",
                contentType = "image/webp",
                fileId = fileId,
                num = index + 1,
                targetDir = request.identifier
            )

            if (fileInfo != null) {
                val imageFile = ImageFile.fromFileInfo(fileInfo = fileInfo, identifier = request.identifier)

                commonImageMapper.insertImageFile(imageFile = imageFile)
            }
        }

        return FileIdResponse(fileId = fileId)
    }

    /**
     * 이미지 목록 조회
     */
    fun getImages(fileId: String): List<FileUrlResponse> {
        return commonImageMapper.selectImageFileUrl(fileId = fileId)
    }
}