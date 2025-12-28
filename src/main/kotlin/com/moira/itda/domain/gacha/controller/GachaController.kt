package com.moira.itda.domain.gacha.controller

import com.moira.itda.domain.gacha.service.GachaService
import com.moira.itda.domain.gacha.dto.response.GachaPageResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 가챠정보 > 가챠 목록
 */
@RestController
class GachaController(
    private val service: GachaService
) {
    /**
     * 가챠정보 > 가챠 목록
     */
    @GetMapping("/api/gacha")
    fun getAll(
        @RequestParam(required = false, defaultValue = "") keyword: String,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "LATEST") sort: String
    ): ResponseEntity<GachaPageResponse> {
        val response = service.getAll(keyword = keyword, page = page, sort = sort)

        return ResponseEntity.ok(response)
    }
}