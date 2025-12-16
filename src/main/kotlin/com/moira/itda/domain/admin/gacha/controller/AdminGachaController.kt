package com.moira.itda.domain.admin.gacha.controller

import com.moira.itda.domain.admin.gacha.dto.request.AdminGachaAddRequest
import com.moira.itda.domain.admin.gacha.dto.request.AdminGachaItemAddRequest
import com.moira.itda.domain.admin.gacha.service.AdminGachaService
import com.moira.itda.global.auth.aop.IsAdmin
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

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
}