package com.moira.itda.domain.gacha_trade_list.controller

import com.moira.itda.domain.gacha_trade_list.dto.response.TradeListPageResponse
import com.moira.itda.domain.gacha_trade_list.service.GachaTradeListService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 거래목록 조회 모달
 */
@RestController
class GachaTradeListController(
    private val service: GachaTradeListService
) {
    /**
     * 가챠상세정보 > 거래목록 조회
     */
    @GetMapping("/api/gacha/{gachaId}/trades")
    fun getTradeList(
        @PathVariable gachaId: String,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false) placeId: String?,
        @RequestParam(required = false, defaultValue = "N") onlyPending: String,
        @RequestParam(required = false) gachaItemId: Long?
    ): ResponseEntity<TradeListPageResponse> {
        val response = service.getTradeList(
            gachaId = gachaId,
            page = page,
            placeId = placeId,
            onlyPending = onlyPending,
            gachaItemId = gachaItemId
        )

        return ResponseEntity.ok(response)
    }
}