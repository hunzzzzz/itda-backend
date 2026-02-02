package com.moira.itda.domain.suggest.delete.controller

import com.moira.itda.domain.suggest.delete.service.SuggestDeleteService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class SuggestDeleteController(
    private val service: SuggestDeleteService
) {
    /**
     * 제안삭제
     */
    @DeleteMapping("/api/me/suggest/{suggestId}/delete")
    fun delete(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable suggestId: String
    ): ResponseEntity<Nothing> {
        service.deleteSuggest(userId = userAuth.userId, suggestId = suggestId)

        return ResponseEntity.ok(null)
    }
}