package com.moira.itda.domain.file.controller;

import com.moira.itda.domain.file.dto.request.ImageFileRequest;
import com.moira.itda.domain.file.dto.response.FileIdResponse;
import com.moira.itda.domain.file.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ImageFileController {
    private final ImageFileService imageFileService;

    @PostMapping("/api/image/files")
    public ResponseEntity<FileIdResponse> uploadFiles(@ModelAttribute ImageFileRequest request) {
        FileIdResponse fileIdResponse = imageFileService.uploadFile(request);

        return ResponseEntity.ok(fileIdResponse);
    }
}
