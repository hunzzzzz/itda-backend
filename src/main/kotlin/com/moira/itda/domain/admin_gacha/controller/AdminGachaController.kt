package com.moira.itda.domain.admin_gacha.controller

import com.moira.itda.domain.admin_gacha.dto.request.AdminGachaAddRequest
import com.moira.itda.domain.admin_gacha.dto.request.AdminGachaItemAddRequest
import com.moira.itda.domain.admin_gacha.dto.request.AdminGachaItemUpdateRequest
import com.moira.itda.domain.admin_gacha.dto.request.AdminGachaUpdateRequest
import com.moira.itda.domain.admin_gacha.dto.response.AdminGachaItemResponse
import com.moira.itda.domain.admin_gacha.dto.response.AdminGachaResponse
import com.moira.itda.domain.admin_gacha.service.AdminGachaService
import com.moira.itda.global.auth.aop.IsAdmin
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 어드민페이지 > 가챠 탭
 */
@RestController
class AdminGachaController(
    private val service: AdminGachaService
) {
    /**
     * 가챠 등록
     */
    @IsAdmin
    @PostMapping("/api/admin/gacha")
    fun add(@RequestBody request: AdminGachaAddRequest): ResponseEntity<Nothing?> {
        service.add(request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }

    /**
     * 가챠아이템 등록
     */
    @IsAdmin
    @PostMapping("/api/admin/gacha/{gachaId}/items")
    fun addItem(
        @PathVariable gachaId: String,
        @RequestBody request: AdminGachaItemAddRequest
    ): ResponseEntity<Nothing> {
        service.addItem(gachaId = gachaId, request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }

    /**
     * 가챠 목록 조회
     */
    @IsAdmin
    @GetMapping("/api/admin/gacha")
    fun getAll(
        @RequestParam(required = false, defaultValue = "") keyword: String
    ): ResponseEntity<List<AdminGachaResponse>> {
        val response = service.getAll(keyword = keyword)

        return ResponseEntity.ok(response)
    }

    /**
     * 가챠아이템 목록 조회
     */
    @IsAdmin
    @GetMapping("/api/admin/gacha/{gachaId}/items")
    fun getItems(@PathVariable gachaId: String): ResponseEntity<List<AdminGachaItemResponse>> {
        val response = service.getItems(gachaId = gachaId)

        return ResponseEntity.ok(response)
    }

    /**
     * 가챠 수정
     */
    @IsAdmin
    @PutMapping("/api/admin/gacha/{gachaId}")
    fun update(
        @PathVariable gachaId: String,
        @RequestBody request: AdminGachaUpdateRequest
    ): ResponseEntity<Nothing?> {
        service.update(gachaId = gachaId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 가챠아이템 수정
     */
    @IsAdmin
    @PutMapping("/api/admin/gacha/{gachaId}/items/{gachaItemId}")
    fun updateItem(
        @PathVariable gachaId: String,
        @PathVariable gachaItemId: Long,
        @RequestBody request: AdminGachaItemUpdateRequest
    ): ResponseEntity<Nothing?> {
        service.updateItem(gachaItemId = gachaItemId, request = request)

        return ResponseEntity.ok(null)
    }

    /**
     * 가챠아이템 삭제
     */
    @IsAdmin
    @DeleteMapping("/api/admin/gacha/{gachaId}/items/{gachaItemId}")
    fun deleteItem(
        @PathVariable gachaId: String,
        @PathVariable gachaItemId: Long
    ): ResponseEntity<Nothing> {
        service.deleteItem(gachaItemId = gachaItemId)

        return ResponseEntity.noContent().build()
    }
}