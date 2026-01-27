package com.moira.itda.domain.common.image.controller

import com.moira.itda.domain.common.image.dto.request.CommonImageUploadRequest
import com.moira.itda.domain.common.image.dto.response.FileIdResponse
import com.moira.itda.domain.common.image.dto.response.FileUrlResponse
import com.moira.itda.domain.common.image.service.CommonImageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CommonImageController(
    private val commonImageService: CommonImageService
) {
    /**
     * 이미지 업로드
     */
    @PostMapping("/api/image")
    fun uploadImage(@ModelAttribute request: CommonImageUploadRequest): ResponseEntity<FileIdResponse> {
        val response = commonImageService.uploadImage(request = request)

        return ResponseEntity.ok(response)
    }

    /**
     * 이미지 목록 조회
     */
    @GetMapping("/api/image/{fileId}")
    fun getImages(@PathVariable fileId: String): ResponseEntity<List<FileUrlResponse>> {
        val response = commonImageService.getImages(fileId = fileId)

        return ResponseEntity.ok(response)
    }
}