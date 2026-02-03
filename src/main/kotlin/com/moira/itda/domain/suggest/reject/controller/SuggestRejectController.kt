package com.moira.itda.domain.suggest.reject.controller

import com.moira.itda.domain.suggest.reject.service.SuggestRejectService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class SuggestRejectController(
    private val service: SuggestRejectService
) {
    /**
     * 제안거절
     */
    @DeleteMapping("/api/suggest/{suggestId}/reject")
    fun reject(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable suggestId: String,
    ): ResponseEntity<Nothing?> {
        service.reject(userId = userAuth.userId, suggestId = suggestId)

        return ResponseEntity.ok(null)
    }
}