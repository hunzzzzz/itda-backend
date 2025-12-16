package com.moira.itda.domain.common.controller

import com.moira.itda.domain.common.dto.request.ImageFileUploadRequest
import com.moira.itda.domain.common.dto.response.FileIdResponse
import com.moira.itda.domain.common.service.CommonService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CommonController(
    private val commonService: CommonService
) {
    /**
     * 공통 > 내 프로필 조회
     */
    @GetMapping("/api/me/simple")
    fun getMyProfile(@UserPrincipal userAuth: UserAuth): ResponseEntity<UserAuth> {
        return ResponseEntity.ok(userAuth)
    }

    /**
     * 공통 > 이미지 파일 업로드
     */
    @PostMapping("/api/image/files")
    fun uploadImage(@ModelAttribute request: ImageFileUploadRequest): ResponseEntity<FileIdResponse> {
        val response = commonService.uploadImage(request = request)

        return ResponseEntity.ok(response)
    }
}