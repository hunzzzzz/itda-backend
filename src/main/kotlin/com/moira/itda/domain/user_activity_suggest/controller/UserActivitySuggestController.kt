package com.moira.itda.domain.user_activity_suggest.controller

import com.moira.itda.domain.user_activity_suggest.dto.response.MyTradeSuggestPageResponse
import com.moira.itda.domain.user_activity_suggest.service.UserActivitySuggestService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 내 활동 > 제안
 */
@RestController
class UserActivitySuggestController(
    private val service: UserActivitySuggestService
) {
    /**
     * 내 활동 > 제안 > 내 제안목록 조회
     */
    @GetMapping("/api/me/trade/suggest")
    fun getMySuggestList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<MyTradeSuggestPageResponse> {
        val response = service.getMySuggestList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }

    /**
     * 내 활동 > 제안 > 제안취소
     */
    @DeleteMapping("/api/me/trade/suggest/{suggestId}/cancel")
    fun cancel(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable suggestId: String
    ): ResponseEntity<Nothing> {
        service.cancelSuggest(userId = userAuth.userId, suggestId = suggestId)

        return ResponseEntity.ok(null)
    }

    /**
     * 내 활동 > 제안 > 제안삭제
     */
    @DeleteMapping("/api/me/trade/suggest/{suggestId}/delete")
    fun delete(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable suggestId: String
    ): ResponseEntity<Nothing> {
        service.deleteSuggest(userId = userAuth.userId, suggestId = suggestId)

        return ResponseEntity.ok(null)
    }
}