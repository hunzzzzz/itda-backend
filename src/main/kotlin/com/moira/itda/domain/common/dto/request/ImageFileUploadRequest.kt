package com.moira.itda.domain.common.dto.request

import org.springframework.web.multipart.MultipartFile

data class ImageFileUploadRequest(
    val files: List<MultipartFile>,
    val identifier: String
)