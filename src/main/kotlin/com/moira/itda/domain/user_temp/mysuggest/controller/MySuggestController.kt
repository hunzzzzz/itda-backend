package com.moira.itda.domain.user_temp.mysuggest.controller

import com.moira.itda.domain.user_temp.mysuggest.service.MySuggestService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

/**
 * 마이페이지 > 내 거래 목록 > 제안 탭
 */
@RestController
class MySuggestController(
    private val mySuggestService: MySuggestService
) {
    /**
     * 마이페이지 > 내 거래 목록 > 제안 > 거래제안 취소
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
     * 마이페이지 > 내 거래 목록 > 제안 > 거래제안 삭제
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