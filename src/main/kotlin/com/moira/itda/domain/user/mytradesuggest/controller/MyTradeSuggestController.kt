package com.moira.itda.domain.user.mytradesuggest.controller

import com.moira.itda.domain.user.mytradesuggest.dto.response.SuggestPageResponse
import com.moira.itda.domain.user.mytradesuggest.service.MyTradeSuggestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달
 */
@RestController
class MyTradeSuggestController(
    private val myTradeSuggestService: MyTradeSuggestService
) {
    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 목록 조회
     */
    @GetMapping("/api/me/trade/{tradeId}")
    fun getMyTradeSuggests(
        @PathVariable tradeId: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<SuggestPageResponse> {
        val response = myTradeSuggestService.getMyTradeSuggests(
            tradeId = tradeId,
            page = page,
        )

        return ResponseEntity.ok(response)
    }

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 거절
     */
    @PutMapping("/api/me/trade/{tradeId}/suggest/{suggestId}/reject")
    fun reject(
        @PathVariable tradeId: String,
        @PathVariable suggestId: Long
    ): ResponseEntity<Nothing> {
        myTradeSuggestService.reject(tradeId = tradeId, suggestId = suggestId)

        return ResponseEntity.ok(null)
    }
}