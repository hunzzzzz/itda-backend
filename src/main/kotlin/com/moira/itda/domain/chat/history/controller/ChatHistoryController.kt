package com.moira.itda.domain.chat.history.controller

import com.moira.itda.domain.chat.history.service.ChatHistoryService
import com.moira.itda.domain.chat.history.dto.response.ChatMessageResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatHistoryController(
    private val service: ChatHistoryService
) {
    /**
     * 이전 채팅내역 조회
     */
    @GetMapping("/api/chat/{chatRoomId}/history")
    fun getChatMessageList(@PathVariable chatRoomId: String): ResponseEntity<List<ChatMessageResponse>> {
        val response = service.getChatMessageList(chatRoomId = chatRoomId)

        return ResponseEntity.ok(response)
    }
}