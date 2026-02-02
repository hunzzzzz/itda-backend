package com.moira.itda.domain.suggest.cancel.controller

import com.moira.itda.domain.suggest.cancel.service.SuggestFirstCancelService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SuggestFirstCancelController(
    private val service: SuggestFirstCancelService
) {
    /**
     * 제안취소 (판매자 응답 전)
     */
    @PutMapping("/api/me/suggest/{suggestId}/cancel")
    fun cancel(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable suggestId: String
    ): ResponseEntity<Nothing> {
        service.cancelSuggest(userId = userAuth.userId, suggestId = suggestId)

        return ResponseEntity.ok(null)
    }
}