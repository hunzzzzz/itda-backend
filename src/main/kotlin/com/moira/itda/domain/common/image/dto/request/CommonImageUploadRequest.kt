package com.moira.itda.domain.common.image.dto.request

import org.springframework.web.multipart.MultipartFile

data class CommonImageUploadRequest(
    val files: List<MultipartFile>,
    val identifier: String
)