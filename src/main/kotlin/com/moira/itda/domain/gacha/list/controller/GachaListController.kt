package com.moira.itda.domain.gacha.list.controller

import com.moira.itda.domain.gacha.list.dto.response.GachaPageResponse
import com.moira.itda.domain.gacha.list.service.GachaListService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 가챠정보 탭 > 가챠목록 페이지
 */
@RestController
class GachaListController(
    private val gachaListService: GachaListService
) {
    /**
     * 가챠정보 > 가챠목록 > 전체 목록 조회
     */
    @GetMapping("/api/gacha")
    fun getAll(
        @RequestParam(required = false, defaultValue = "") keyword: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<GachaPageResponse> {
        val response = gachaListService.getAll(keyword = keyword, page = page)

        return ResponseEntity.ok(response)
    }
}