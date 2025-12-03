package com.moira.itda.domain.file.service;

import com.moira.itda.domain.file.dto.request.ImageFileRequest;
import com.moira.itda.domain.file.dto.response.FileIdResponse;
import com.moira.itda.domain.file.mapper.ImageFileMapper;
import com.moira.itda.global.entity.ImageFile;
import com.moira.itda.global.file.FileInfo;
import com.moira.itda.global.file.FileValidator;
import com.moira.itda.global.file.LocalFileStorageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageFileService {
    @Value("${spring.profiles.active}")
    private String profilesActive;

    private final FileValidator fileValidator;
    private final ImageFileMapper imageFileMapper;
    private final LocalFileStorageHandler localFileStorageHandler;

    @Transactional
    public FileIdResponse uploadFile(ImageFileRequest request) {
        FileInfo fileInfo = null;
        String fileId = UUID.randomUUID().toString();

        // [1] 유효성 검사
        fileValidator.validate(request.files());

        // [2] 이미지 파일 저장 (파일 시스템)
        for (int i = 0; i < request.files().size(); i++) {
            MultipartFile file = request.files().get(i);

            if ("local".equals(profilesActive)) {
                fileInfo = localFileStorageHandler.saveFile(file, fileId, i + 1, request.identifier());
            } else if ("prod".equals(profilesActive)) {
                // TODO: AWS S3 로직 구현
            }

            // [3] 이미지 파일 저장 (DB)
            if (fileInfo != null) {
                ImageFile imageFile = ImageFile.builder()
                        .fileId(fileInfo.fileId())
                        .identifier(request.identifier())
                        .originalFileName(fileInfo.originalFileName())
                        .storedFileName(fileInfo.storedFileName())
                        .size(fileInfo.size())
                        .fileUrl(fileInfo.fileUrl())
                        .createdAt(ZonedDateTime.now())
                        .build();

                imageFileMapper.insertImageFile(imageFile);
            }
        }

        // [3] 파일 ID 리턴
        return new FileIdResponse(fileId);
    }
}
