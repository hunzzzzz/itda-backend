package com.moira.itda.global.file;

import lombok.Builder;

@Builder
public record FileInfo(
        String fileId,
        String originalFileName,
        String storedFileName,
        Long size,
        String fileUrl
) {
}
