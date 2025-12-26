package com.moira.itda.domain.user.mysuggest.controller

import com.moira.itda.domain.user.mysuggest.dto.response.MySuggestPageResponse
import com.moira.itda.domain.user.mysuggest.service.MySuggestService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 마이페이지 > 내 거래 목록 > 제안 탭
 */
@RestController
class MySuggestController(
    private val mySuggestService: MySuggestService
) {
    /**
     * 마이페이지 > 내 거래 목록 > 제안 > 거래 제안 목록 조회
     */
    @GetMapping("/api/me/trade/suggest")
    fun getTradeSuggests(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<MySuggestPageResponse> {
        val response = mySuggestService.getTradeSuggests(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }

    /**
     * 마이페이지 > 내 거래 목록 > 제안 > 거래 제안 취소
     */
    @DeleteMapping("/api/me/trade/suggest/{suggestId}/cancel")
    fun cancelSuggest(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable suggestId: String
    ): ResponseEntity<Nothing> {
        mySuggestService.cancelSuggest(userId = userAuth.userId, suggestId = suggestId)

        return ResponseEntity.ok(null)
    }

    /**
     * 마이페이지 > 내 거래 목록 > 제안 > 거래 제안 삭제
     */
    @DeleteMapping("/api/me/trade/suggest/{suggestId}/delete")
    fun deleteSuggest(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable suggestId: String
    ): ResponseEntity<Nothing> {
        mySuggestService.deleteSuggest(userId = userAuth.userId, suggestId = suggestId)

        return ResponseEntity.ok(null)
    }
}