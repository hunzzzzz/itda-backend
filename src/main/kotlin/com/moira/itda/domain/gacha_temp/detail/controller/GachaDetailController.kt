package com.moira.itda.domain.gacha_temp.detail.controller

import com.moira.itda.domain.gacha_temp.detail.dto.response.TradePageResponse
import com.moira.itda.domain.gacha_temp.detail.service.GachaDetailService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 가챠정보 탭 > 가챠목록 > 상세정보
 */
@RestController
class GachaDetailController(
    private val gachaDetailService: GachaDetailService
) {
    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 거래 목록 조회
     */
    @GetMapping("/api/gacha/{gachaId}/trades")
    fun getTrades(
        @PathVariable gachaId: String,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "N") onlyPending: String,
        @RequestParam(required = false) gachaItemId: Long?
    ): ResponseEntity<TradePageResponse> {
        val response = gachaDetailService.getTrades(
            gachaId = gachaId,
            page = page,
            onlyPending = onlyPending,
            gachaItemId = gachaItemId
        )

        return ResponseEntity.ok(response)
    }
}