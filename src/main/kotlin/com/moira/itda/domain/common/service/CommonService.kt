package com.moira.itda.domain.common.service

import com.moira.itda.domain.common.dto.request.ImageFileUploadRequest
import com.moira.itda.domain.common.dto.response.CodeDetailResponse
import com.moira.itda.domain.common.dto.response.FileIdResponse
import com.moira.itda.domain.common.mapper.CommonMapper
import com.moira.itda.global.entity.ImageFile
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.file.component.AwsS3Handler
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class CommonService(
    private val awsS3Handler: AwsS3Handler,
    private val commonMapper: CommonMapper
) {
    companion object {
        private const val MAX_FILE_SIZE = 5L * 1024 * 1024 // 5L
        private val ALLOWED_EXTENSIONS: Set<String> = setOf("jpg", "jpeg", "png")
    }

    /**
     * 공통 > 이미지 파일 업로드 > 유효성 검사
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

            println(extension)

            if (extension.isEmpty() || !ALLOWED_EXTENSIONS.contains(extension)) {
                throw ItdaException(ErrorCode.UNSUPPORTED_FILE_EXTENSION)
            }
        }
    }

    /**
     * 공통 > 이미지 파일 업로드
     */
    fun uploadImage(request: ImageFileUploadRequest): FileIdResponse {
        // [1] 유효성 검사
        this.validate(files = request.files)

        // [2] 이미지 파일 저장 (AWS S3)
        val fileId = UUID.randomUUID().toString()

        request.files.forEachIndexed { index, file ->
            val fileInfo = awsS3Handler.upload(
                file = file,
                fileId = fileId,
                num = index + 1,
                targetDir = request.identifier
            )

            if (fileInfo != null) {
                val imageFile = ImageFile.fromFileInfo(fileInfo = fileInfo, identifier = request.identifier)

                commonMapper.insertImageFile(imageFile = imageFile)
            }
        }

        return FileIdResponse(fileId = fileId)
    }

    /**
     * 공통 > 공통코드 조회
     */
    @Transactional(readOnly = true)
    fun getCode(key: String): List<CodeDetailResponse> {
        return commonMapper.selectCodeDetailList(key = key)
    }
}