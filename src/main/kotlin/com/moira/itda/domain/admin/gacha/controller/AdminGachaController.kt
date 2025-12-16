package com.moira.itda.domain.admin.gacha.controller

import com.moira.itda.domain.admin.gacha.dto.request.AdminGachaAddRequest
import com.moira.itda.domain.admin.gacha.dto.request.AdminGachaItemAddRequest
import com.moira.itda.domain.admin.gacha.dto.request.AdminGachaItemUpdateRequest
import com.moira.itda.domain.admin.gacha.dto.request.AdminGachaUpdateRequest
import com.moira.itda.domain.admin.gacha.dto.response.AdminGachaItemResponse
import com.moira.itda.domain.admin.gacha.dto.response.AdminGachaResponse
import com.moira.itda.domain.admin.gacha.service.AdminGachaService
import com.moira.itda.global.auth.aop.IsAdmin
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 어드민 > 가챠정보 페이지
 */
@RestController
class AdminGachaController(
    private val adminGachaService: AdminGachaService
) {
    /**
     * 어드민 페이지 > 가챠정보 > 등록
     */
    @IsAdmin
    @PostMapping("/api/admin/gacha")
    fun add(@RequestBody request: AdminGachaAddRequest): ResponseEntity<Nothing> {
        adminGachaService.add(request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }

    /**
     * 어드민 페이지 > 가챠정보 > 하위 아이템 등록
     */
    @IsAdmin
    @PostMapping("/api/admin/gacha/{gachaId}/items")
    fun addItem(
        @PathVariable gachaId: String,
        @RequestBody request: AdminGachaItemAddRequest
    ): ResponseEntity<Nothing> {
        adminGachaService.addItem(gachaId = gachaId, request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }

    /**
     * 어드민 페이지 > 가챠정보 > 전체 조회
     */
    @IsAdmin
    @GetMapping("/api/admin/gacha")
    fun getAll(
        @RequestParam(required = false, defaultValue = "") keyword: String
    ): ResponseEntity<List<AdminGachaResponse>> {
        val response = adminGachaService.getAll(keyword = keyword)

        return ResponseEntity.ok(response)
    }

    /**
     * 어드민 페이지 > 가챠정보 > 하위 아이템 목록 조회
     */
    @IsAdmin
    @GetMapping("/api/admin/gacha/{gachaId}/items")
    fun getItems(@PathVariable gachaId: String): ResponseEntity<List<AdminGachaItemResponse>> {
        val response = adminGachaService.getItems(gachaId = gachaId)

        return ResponseEntity.ok(response)
    }

    /**
     * 어드민 페이지 > 가챠정보 > 수정
     */
    @IsAdmin
    @PutMapping("/api/admin/gacha/{gachaId}")
    fun update(
        @PathVariable gachaId: String,
        @RequestBody request: AdminGachaUpdateRequest
    ): ResponseEntity<Nothing> {
        adminGachaService.update(gachaId = gachaId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 어드민 페이지 > 가챠정보 > 하위 아이템 수정
     */
    @IsAdmin
    @PutMapping("/api/admin/gacha/{gachaId}/items/{itemId}")
    fun updateItem(
        @PathVariable gachaId: String,
        @PathVariable itemId: Long,
        @RequestBody request: AdminGachaItemUpdateRequest
    ): ResponseEntity<Nothing> {
        adminGachaService.updateItem(gachaId = gachaId, itemId = itemId, request = request)

        return ResponseEntity.ok(null)
    }
}