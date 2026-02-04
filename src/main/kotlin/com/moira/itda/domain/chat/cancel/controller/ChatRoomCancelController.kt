package com.moira.itda.domain.chat.cancel.controller

import com.moira.itda.domain.chat.cancel.dto.request.CancelRequest
import com.moira.itda.domain.chat.cancel.service.ChatRoomCancelService
import com.moira.itda.domain.chat.cancel.dto.response.ChatRoomIdResponse
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatRoomCancelController(
    private val service: ChatRoomCancelService
) {
    /**
     * 거래취소
     */
    @PostMapping("/api/chat/{chatRoomId}/cancel")
    fun cancel(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable chatRoomId: String,
        @RequestBody request: CancelRequest
    ): ResponseEntity<ChatRoomIdResponse> {
        service.cancel(userId = userAuth.userId, chatRoomId = chatRoomId, request = request)

        return ResponseEntity.ok().body(ChatRoomIdResponse(chatRoomId = chatRoomId))
    }
}