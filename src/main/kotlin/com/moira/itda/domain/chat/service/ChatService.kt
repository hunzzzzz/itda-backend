package com.moira.itda.domain.chat.service

import com.moira.itda.domain.chat.dto.request.ChatMessageRequest
import com.moira.itda.domain.chat.mapper.ChatMapper
import com.moira.itda.global.entity.ChatMessage
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatService(
    private val chatMapper: ChatMapper,
    private val messageTemplate: SimpMessagingTemplate
) {
    /**
     * 채팅방 > 메시지 전송
     */
    @Transactional
    fun sendMessage(chatRoomId: String, request: ChatMessageRequest) {
        // [1] ChatMessage 저장
        val chatMessage = ChatMessage.fromChatMessageRequest(chatRoomId = chatRoomId, request = request)
        chatMapper.insertChatMessage(chatMessage = chatMessage)

        // [2] /sub/chat/${chatRoomId}/message를 구독중인 사람들에게 메시지 전달
        messageTemplate.convertAndSend("/sub/chat/$chatRoomId/message", chatMessage)
    }
}