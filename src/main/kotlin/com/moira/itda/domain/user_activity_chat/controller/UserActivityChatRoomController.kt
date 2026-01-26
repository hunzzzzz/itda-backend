package com.moira.itda.domain.user_activity_chat.controller

import com.moira.itda.domain.user_activity_chat.dto.response.ChatRoomPageResponse
import com.moira.itda.domain.user_activity_chat.service.UserActivityChatRoomService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 내 활동 > 채팅 탭
 */
@RestController
class UserActivityChatRoomController(
    private val service: UserActivityChatRoomService
) {
    /**
     * 채팅방 목록 조회
     */
    @GetMapping("/api/me/chat/room")
    fun getChatRoomList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<ChatRoomPageResponse?> {
        val response = service.getChatRoomList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }
}