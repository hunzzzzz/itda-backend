package com.moira.itda.domain.gacha_list.controller

import com.moira.itda.domain.gacha_list.dto.response.GachaListPageResponse
import com.moira.itda.domain.gacha_list.service.GachaListService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 가챠정보 페이지
 */
@RestController
class GachaListController(
    private val service: GachaListService
) {
    /**
     * 가챠목록 조회
     */
    @GetMapping("/api/gacha")
    fun getAll(
        @RequestParam(required = false, defaultValue = "") keyword: String,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "LATEST") sort: String
    ): ResponseEntity<GachaListPageResponse> {
        val response = service.getGachaList(keyword = keyword, page = page, sort = sort)

        return ResponseEntity.ok(response)
    }
}