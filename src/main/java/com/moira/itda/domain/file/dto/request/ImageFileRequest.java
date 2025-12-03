package com.moira.itda.domain.file.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ImageFileRequest(
        List<MultipartFile> files,
        String identifier
) {
}
