package com.moira.itda.domain.suggest.approve.controller

import com.moira.itda.domain.suggest.approve.dto.request.ApproveRequest
import com.moira.itda.domain.suggest.approve.dto.response.ChatRoomIdResponse
import com.moira.itda.domain.suggest.approve.service.SuggestApproveService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SuggestApproveController(
    private val service: SuggestApproveService
) {
    /**
     * 제안승인
     */
    @PostMapping("/api/suggest/{suggestId}/approve")
    fun approve(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable suggestId: String,
        @RequestBody request: ApproveRequest
    ): ResponseEntity<ChatRoomIdResponse> {
        val response = service.approve(
            userId = userAuth.userId,
            suggestId = suggestId,
            request = request
        )

        return ResponseEntity.ok(response)
    }
}