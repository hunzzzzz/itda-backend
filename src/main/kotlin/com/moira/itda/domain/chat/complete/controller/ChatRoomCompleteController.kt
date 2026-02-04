package com.moira.itda.domain.chat.complete.controller

import com.moira.itda.domain.chat.complete.dto.request.CompleteRequest
import com.moira.itda.domain.chat.complete.service.ChatRoomCompleteService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatRoomCompleteController(
    private val service: ChatRoomCompleteService
) {
    /**
     * 거래완료
     */
    @PostMapping("/api/chat/{chatRoomId}/complete")
    fun completeTrade(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable chatRoomId: String,
        @RequestBody request: CompleteRequest
    ): ResponseEntity<Nothing> {
        service.completeTrade(userId = userAuth.userId, chatRoomId = chatRoomId, request = request)

        return ResponseEntity.ok(null)
    }
}