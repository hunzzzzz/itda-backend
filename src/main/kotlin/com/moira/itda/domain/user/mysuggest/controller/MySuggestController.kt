package com.moira.itda.domain.user.mysuggest.controller

import com.moira.itda.domain.user.mysuggest.dto.response.MySuggestPageResponse
import com.moira.itda.domain.user.mysuggest.service.MySuggestService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 마이페이지 > 내 거래 목록 > 거래 제안 목록 탭
 */
@RestController
class MySuggestController(
    private val mySuggestService: MySuggestService
) {
    /**
     * 마이페이지 > 내 거래 목록 > 거래 제안 목록 탭 > 거래 제안 목록 조회
     */
    @GetMapping("/api/me/trade/suggest")
    fun getTradeSuggests(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<MySuggestPageResponse> {
        val response = mySuggestService.getTradeSuggests(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }
}