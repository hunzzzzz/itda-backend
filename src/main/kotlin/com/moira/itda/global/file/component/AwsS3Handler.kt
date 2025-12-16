package com.moira.itda.global.file.component

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.file.dto.FileInfo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder

@Component
class AwsS3Handler(
    private val amazonS3: AmazonS3
) {
    @Value("\${cloud.aws.s3.bucketName}")
    private lateinit var bucketName: String

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * AWS S3 버킷에 파일 업로드
     */
    fun upload(file: MultipartFile, fileId: String, num: Int, targetDir: String): FileInfo? {
        try {
            file.inputStream.use { inputStream ->
                // [1] 파일 정보 추출
                // 원본 파일명 (사용자가 지정한 파일명)
                val originalFileName = file.originalFilename ?: throw ItdaException(ErrorCode.INVALID_FILE)
                // 파일 확장자
                val extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1)
                // 파일 시스템에 저장되는 파일명 (fileId_num.extension)
                val storedFileName = "${fileId}_${num}.${extension}"
                // S3 객체 Key (디렉터리/파일명)
                val s3Key = "${targetDir}/${storedFileName}"

                // [2] Metadata 생성
                val metadata = ObjectMetadata()
                metadata.contentType = file.contentType
                metadata.contentLength = file.size

                // [3] 업로드
                val s3Object = PutObjectRequest(bucketName, s3Key, inputStream, metadata)
                amazonS3.putObject(s3Object)

                // [4] S3 Url 생성
                val url = amazonS3.getUrl(bucketName, s3Key)
                val fileUrl = url.toString()

                // [5] FileInfo 객체 생성 및 리턴
                return FileInfo(
                    fileId = fileId,
                    originalFileName = originalFileName,
                    storedFileName = storedFileName,
                    size = file.size,
                    fileUrl = fileUrl
                )
            }
        } catch (e: Exception) {
            log.error("파일 저장 실패: {}", e.message, e)
            throw ItdaException(ErrorCode.FILE_SAVE_FAILED)
        }
    }

    /**
     * AWS S3 버킷에 파일 삭제
     */
    fun delete(fileUrl: String) {
        try {
            // [1] URL 파싱 후 경로(URI) 부분만 추출
            val uriComponents = UriComponentsBuilder.fromUriString(fileUrl).build()
            val path = uriComponents.path ?: throw ItdaException(ErrorCode.FILE_DELETE_FAILED)
            val s3Key = path.substring(1) // 맨 앞자리 슬래시(/) 제거

            // [2] 삭제
            amazonS3.deleteObject(bucketName, s3Key)
        } catch (e: Exception) {
            log.error("파일 삭제 실패: {}", e.message, e)
            throw ItdaException(ErrorCode.FILE_DELETE_FAILED)
        }
    }
}