package com.moira.itda.domain.suggest_list.controller

import com.moira.itda.domain.suggest_list.dto.request.SuggestYnRequest
import com.moira.itda.domain.suggest_list.dto.response.ChatRoomIdResponse
import com.moira.itda.domain.suggest_list.service.SuggestApproveService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 제안목록 조회 모달
 */
@RestController
class SuggestApproveController(
    private val service: SuggestApproveService
) {
    /**
     * 제안승인
     */
    @PostMapping("/api/trades/{tradeId}/suggests/approve")
    fun approve(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @RequestBody request: SuggestYnRequest
    ): ResponseEntity<ChatRoomIdResponse> {
        val response = service.approve(
            userId = userAuth.userId,
            tradeId = tradeId,
            request = request
        )

        return ResponseEntity.ok(response)
    }

    /**
     * 제안거절
     */
    @PostMapping("/api/trades/{tradeId}/suggests/reject")
    fun reject(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable tradeId: String,
        @RequestBody request: SuggestYnRequest
    ): ResponseEntity<Nothing?> {
        service.reject(userId = userAuth.userId, tradeId = tradeId, request = request)

        return ResponseEntity.ok(null)
    }
}