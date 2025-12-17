package com.moira.itda.domain.admin.code.controller

import com.moira.itda.domain.admin.code.dto.request.AdminCodeAddRequest
import com.moira.itda.domain.admin.code.dto.request.AdminCodeDetailAddRequest
import com.moira.itda.domain.admin.code.dto.request.AdminCodeDetailUpdateRequest
import com.moira.itda.domain.admin.code.dto.response.AdminCodeDetailResponse
import com.moira.itda.domain.admin.code.dto.response.AdminCodeResponse
import com.moira.itda.domain.admin.code.service.AdminCodeService
import com.moira.itda.global.auth.aop.IsAdmin
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 어드민 페이지 > 공통코드 탭
 */
@RestController
class AdminCodeController(
    private val adminCodeService: AdminCodeService
) {
    /**
     * 어드민 페이지 > 공통코드 > 등록
     */
    @IsAdmin
    @PostMapping("/api/admin/code")
    fun add(@RequestBody request: AdminCodeAddRequest): ResponseEntity<Nothing> {
        adminCodeService.add(request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }

    /**
     * 어드민 페이지 > 공통코드 > 세부코드 등록
     */
    @IsAdmin
    @PostMapping("/api/admin/code/{key}")
    fun addDetail(@PathVariable key: String, @RequestBody request: AdminCodeDetailAddRequest): ResponseEntity<Nothing> {
        adminCodeService.addDetail(key = key, request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }

    /**
     * 어드민 페이지 > 공통코드 > 전체 조회
     */
    @IsAdmin
    @GetMapping("/api/admin/code")
    fun getAll(): ResponseEntity<List<AdminCodeResponse>> {
        val response = adminCodeService.getAll()

        return ResponseEntity.ok(response)
    }

    /**
     * 어드민 페이지 > 공통코드 > 세부코드 목록 조회
     */
    @IsAdmin
    @GetMapping("/api/admin/code/{key}")
    fun getDetails(@PathVariable key: String): ResponseEntity<List<AdminCodeDetailResponse>> {
        val response = adminCodeService.getDetails(key = key)

        return ResponseEntity.ok(response)
    }

    /**
     * 어드민 페이지 > 공통코드 > 세부코드 수정
     */
    @IsAdmin
    @PutMapping("/api/admin/code/{key}/details/{detailId}")
    fun updateDetail(
        @PathVariable key: String,
        @PathVariable detailId: Long,
        @RequestBody request: AdminCodeDetailUpdateRequest
    ): ResponseEntity<Nothing> {
        adminCodeService.updateDetail(key = key, detailId = detailId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 어드민 페이지 > 공통코드 > 삭제
     */
    @IsAdmin
    @DeleteMapping("/api/admin/code/{key}")
    fun delete(@PathVariable key: String): ResponseEntity<Nothing> {
        adminCodeService.delete(key = key)

        return ResponseEntity.noContent().build()
    }

    /**
     * 어드민 페이지 > 공통코드 > 세부코드 삭제
     */
    @IsAdmin
    @DeleteMapping("/api/admin/code/{key}/details/{detailId}")
    fun deleteDetail(@PathVariable key: String, @PathVariable detailId: Long): ResponseEntity<Nothing> {
        adminCodeService.deleteDetail(key = key, detailId = detailId)

        return ResponseEntity.noContent().build()
    }
}