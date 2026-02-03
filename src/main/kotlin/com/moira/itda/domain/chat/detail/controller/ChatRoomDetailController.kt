package com.moira.itda.domain.chat.detail.controller

import com.moira.itda.domain.chat.detail.service.ChatRoomDetailService
import com.moira.itda.domain.chat.detail.dto.response.ChatRoomDetailResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatRoomDetailController(
    private val service: ChatRoomDetailService
) {
    /**
     * 채팅방 상단정보 조회
     */
    @GetMapping("/api/chat/{chatRoomId}")
    fun getChatRoomDetail(@PathVariable chatRoomId: String): ResponseEntity<ChatRoomDetailResponse> {
        val response = service.getChatRoomDetail(chatRoomId = chatRoomId)

        return ResponseEntity.ok(response)
    }
}