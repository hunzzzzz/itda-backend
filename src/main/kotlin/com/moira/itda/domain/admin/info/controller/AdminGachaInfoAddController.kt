package com.moira.itda.domain.admin.info.controller

import com.moira.itda.domain.admin.info.dto.response.AdminGachaInfoAddResponse
import com.moira.itda.domain.admin.info.service.AdminGachaInfoAddService
import com.moira.itda.global.auth.aop.IsAdmin
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 어드민 페이지 > 정보등록요청 탭
 */
@RestController
class AdminGachaInfoAddController(
    private val adminGachaInfoAddService: AdminGachaInfoAddService
) {
    /**
     * 어드민 페이지 > 정보등록요청 탭 > 정보등록요청 목록 조회
     */
    @IsAdmin
    @GetMapping("/admin/gacha/info/add")
    fun getAddInfos(): ResponseEntity<List<AdminGachaInfoAddResponse>> {
        val response = adminGachaInfoAddService.getAddInfos()

        return ResponseEntity.ok(response)
    }
}