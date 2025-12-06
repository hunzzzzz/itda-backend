package com.moira.itda.global.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.moira.itda.global.exception.ErrorCode;
import com.moira.itda.global.exception.ItdaException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;

@RequiredArgsConstructor
@Component
public class AwsS3Handler {
    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    private final AmazonS3 amazonS3;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * MultipartFile로부터 OriginalFileName을 추출
     */
    private String getOriginalFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null) {
            throw new ItdaException(ErrorCode.INVALID_FILE);
        }

        return originalFileName;
    }

    /**
     * AWS S3 버킷에 파일 업로드
     */
    public FileInfo upload(MultipartFile file, String fileId, int num, String targetDir) {
        try (InputStream inputStream = file.getInputStream()) {
            // [1] 파일 정보 추출
            // 원본 파일명 (사용자가 지정한 파일명)
            String originalFileName = this.getOriginalFileName(file);
            // 파일 확장자
            String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            // 파일 시스템에 저장되는 파일명 (fileId_num.extension)
            String storedFileName = "%s_%s.%s".formatted(fileId, num, extension);
            // S3 객체 Key (디렉터리/파일명)
            String s3Key = "%s/%s".formatted(targetDir, storedFileName);

            // [2] Metadata 생성
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            // [3] 업로드
            PutObjectRequest s3Object = new PutObjectRequest(bucketName, s3Key, inputStream, metadata);
            amazonS3.putObject(s3Object);

            // [4] S3 Url 생성
            URL url = amazonS3.getUrl(bucketName, storedFileName);
            String fileUrl = url.toString();

            // [5] FileInfo 객체 생성 및 리턴
            return FileInfo.builder()
                    .fileId(fileId)
                    .originalFileName(originalFileName)
                    .storedFileName(storedFileName)
                    .size(file.getSize())
                    .fileUrl(fileUrl)
                    .build();
        } catch (Exception e) {
            log.error("파일 저장 실패: {}", e.getMessage(), e);
            throw new ItdaException(ErrorCode.FILE_SAVE_FAILED);
        }
    }

    public void delete() {
        // TODO
    }
}
