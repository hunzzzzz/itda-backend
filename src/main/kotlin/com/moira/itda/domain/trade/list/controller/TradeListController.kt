package com.moira.itda.domain.trade.list.controller

import com.moira.itda.domain.trade.list.dto.response.TradeListPageResponse
import com.moira.itda.domain.trade.list.service.TradeListService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TradeListController(
    private val service: TradeListService
) {
    /**
     * 거래목록 조회
     */
    @GetMapping("/api/gacha/{gachaId}/trade")
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