package com.moira.itda.domain.gacha.detail.controller

import com.moira.itda.domain.gacha.detail.dto.response.GachaDetailResponse
import com.moira.itda.domain.gacha.detail.service.GachaDetailService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/**
 * 가챠정보 탭 > 가챠목록 > 상세정보 페이지
 */
@RestController
class GachaDetailController(
    private val gachaDetailService: GachaDetailService
) {
    /**
     * 가챠정보 > 가챠목록 > 상세정보
     */
    @GetMapping("/api/gacha/{gachaId}")
    fun get(
        @PathVariable gachaId: String,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    ): ResponseEntity<GachaDetailResponse> {
        val response = gachaDetailService.get(
            gachaId = gachaId,
            httpServletRequest = httpServletRequest,
            httpServletResponse = httpServletResponse
        )

        return ResponseEntity.ok(response)
    }
}