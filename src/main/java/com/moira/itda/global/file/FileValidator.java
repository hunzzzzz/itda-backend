package com.moira.itda.global.file;

import com.moira.itda.global.exception.ErrorCode;
import com.moira.itda.global.exception.ItdaException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Component
public class FileValidator {
    private static final Long MAX_FILE_SIZE = 5L * 1024 * 1024; // 5MB
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png");

    public void validate(List<MultipartFile> files) {
        for (MultipartFile file : files) {
            this.validateFiles(files);
            this.validateOriginalFileName(file);
            this.validateSize(file);
            this.validateExtension(file);
        }
    }

    /**
     * null 체크
     */
    private void validateFiles(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            throw new ItdaException(ErrorCode.INVALID_FILE);
        }
    }

    /**
     * null 체크
     */
    private void validateOriginalFileName(MultipartFile file) {
        if (file.getOriginalFilename() == null) {
            throw new ItdaException(ErrorCode.INVALID_FILE);
        }
    }

    /**
     * 파일 크기 검사 (최대 5MB)
     */
    private void validateSize(MultipartFile file) {
        if (file.isEmpty() || file.getSize() > MAX_FILE_SIZE) {
            throw new ItdaException(ErrorCode.FILE_SIZE_LIMIT_EXCEEDED);
        }
    }

    /**
     * 파일 확장자 검사 (jpg, jpeg, png)
     */
    private void validateExtension(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;

        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
        if (extension.isEmpty() || !ALLOWED_EXTENSIONS.contains(extension)) {
            throw new ItdaException(ErrorCode.UNSUPPORTED_FILE_EXTENSION);
        }
    }
}
