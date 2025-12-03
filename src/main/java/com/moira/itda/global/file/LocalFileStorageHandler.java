package com.moira.itda.global.file;

import com.moira.itda.global.exception.ErrorCode;
import com.moira.itda.global.exception.ItdaException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LocalFileStorageHandler {
    @Value("${file.upload.directory}")
    private String uploadDirectory;

    // 절대 경로 (ex. C:/workspace/itda/local/upload)
    private Path absoluteDirectory;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * init -> 파일 디렉터리 생성
     */
    @PostConstruct
    public void init() {
        this.absoluteDirectory = Paths.get(uploadDirectory).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.absoluteDirectory);
            log.info("로컬 파일 저장 디렉터리 생성 완료: {}", this.absoluteDirectory);
        } catch (Exception e) {
            log.error("로컬 파일 저장 디렉터리 생성 실패: {} {}", this.absoluteDirectory, e.getMessage());
            throw new ItdaException(ErrorCode.LOCAL_FILE_SYSTEM_ERROR);
        }
    }

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
     * 파일 저장
     */
    public FileInfo saveFile(MultipartFile file, String fileId, int num, String targetDir) {
        // 원본 파일명 (사용자가 지정한 파일명)
        String originalFileName = this.getOriginalFileName(file);
        // 파일 확장자
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        // 파일 시스템에 저장되는 파일명 (fileId_num.extension)
        String storedFileName = "%s_%s.%s".formatted(fileId, num, extension);
        // 하위 경로를 포함한 최종 디렉터리 (ex. C:/workspace/itda/local/upload/images)
        Path targetDirectory = this.absoluteDirectory.resolve(targetDir).normalize();

        log.info("==================== 파일 정보 ====================");
        log.info("originalFileName: {}", originalFileName);
        log.info("fileExtension   : {}", extension);
        log.info("storedFileName  : {}", storedFileName);
        log.info("targetDirectory : {}", targetDirectory);

        try {
            // [1] 최종 디렉터리 생성
            Files.createDirectories(targetDirectory);

            // [2] 최종 디렉터리를 Path로 변환
            Path targetPath = targetDirectory.resolve(storedFileName).normalize();

            // [3] 파일 저장
            file.transferTo(targetPath.toFile());
            log.info("파일 저장 성공: {}", targetPath);

            // [4] DB 저장을 위한 DTO 반환
            return FileInfo.builder()
                    .fileId(fileId)
                    .originalFileName(originalFileName)
                    .storedFileName(storedFileName)
                    .size(file.getSize())
                    .fileUrl(targetPath.toString())
                    .build();
        } catch (Exception e) {
            log.error("파일 저장 실패: {}", e.getMessage(), e);
            throw new ItdaException(ErrorCode.FILE_SAVE_FAILED);
        }
    }

    /**
     * 파일 삭제
     */
    public void deleteFile(String fileUrl) {
        try {
            // [1] DB에 저장된 파일 경로를 Path로 변환
            Path filePath = Paths.get(fileUrl).normalize();

            // [2] 파일 경로가 지정된 디렉터리 내에 있는지 확인
            // fileUrl에 경로 조작을 넣어 서버의 민감한 파일을 삭제하는 것을 방지
            if (!filePath.startsWith(this.absoluteDirectory)) {
                log.warn("보안 위험: 삭제 요청 경로가 지정된 업로드 위치를 벗어납니다. {}", fileUrl);
                throw new ItdaException(ErrorCode.FILE_DELETE_FORBIDDEN);
            }

            // [3] 파일 삭제
            boolean isDeleted = Files.deleteIfExists(filePath);

            if (isDeleted) {
                log.info("파일 삭제 성공: {}", filePath);
            } else {
                log.error("파일 삭제 실패: {}", filePath);
                throw new ItdaException(ErrorCode.FILE_DELETE_FAILED);
            }
        } catch (Exception e) {
            log.error("파일 삭제 실패: {}", e.getMessage(), e);
            throw new ItdaException(ErrorCode.FILE_DELETE_FAILED);
        }
    }
}