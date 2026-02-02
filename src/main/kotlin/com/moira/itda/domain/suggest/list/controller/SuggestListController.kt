package com.moira.itda.domain.suggest.list.controller

import com.moira.itda.domain.suggest.list.dto.response.SuggestListPageResponse
import com.moira.itda.domain.suggest.list.service.SuggestListService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SuggestListController(
    private val service: SuggestListService
) {
    /**
     * 제안목록 조회
     */
    @GetMapping("/api/trade/{tradeId}/suggests")
    fun getSuggestList(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<SuggestListPageResponse> {
        val response = service.getSuggestList(
            userId = userAuth.userId,
            tradeId = tradeId,
            page = page
        )

        return ResponseEntity.ok(response)
    }

    /**
     * 내 제안목록 조회
     */
    @GetMapping("/api/me/suggests")
    fun getMySuggestList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<SuggestListPageResponse> {
        val response = service.getMySuggestList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }
}