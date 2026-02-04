package com.moira.itda.domain.chat.list.controller

import com.moira.itda.domain.chat.list.dto.response.ChatRoomPageResponse
import com.moira.itda.domain.chat.list.service.ChatRoomListService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatRoomListController(
    private val service: ChatRoomListService
) {
    /**
     * 내 채팅방 목록 조회
     */
    @GetMapping("/api/me/chatroom")
    fun getChatRoomList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<ChatRoomPageResponse?> {
        val response = service.getChatRoomList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }
}