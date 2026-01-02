package com.moira.itda.global.file.component

import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.file.dto.FileInfo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetUrlRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest

@Component
class AwsS3Handler(
    private val s3Client: S3Client
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

                // [2] PutObjectRequest 생성
                val s3Object = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .contentType(file.contentType)
                    .contentLength(file.size)
                    .build()

                // [3] AWS S3 이미지 저장
                val response = s3Client.putObject(s3Object, RequestBody.fromBytes(file.bytes))

                // [4] 응답값 검증
                if (response.sdkHttpResponse().isSuccessful) {
                    // [4-1] 성공 시, 이미지 URL 추출
                    val urlRequest = GetUrlRequest.builder()
                        .bucket(bucketName)
                        .key(s3Key)
                        .build()

                    val url = s3Client.utilities().getUrl(urlRequest)
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
                // [4-2] 실패 시, 에러 처리
                else {
                    throw ItdaException(ErrorCode.FILE_SAVE_FAILED)
                }
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
            val deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build()

            s3Client.deleteObject(deleteRequest)
        } catch (e: Exception) {
            log.error("파일 삭제 실패: {}", e.message, e)
            throw ItdaException(ErrorCode.FILE_DELETE_FAILED)
        }
    }
}