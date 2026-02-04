package com.moira.itda.domain.chat.send.service

import com.moira.itda.domain.chat.send.mapper.ChatSendMapper
import com.moira.itda.domain.chat.send.dto.request.MessageRequest
import com.moira.itda.global.entity.ChatMessage
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatSendService(
    private val mapper: ChatSendMapper,
    private val messageTemplate: SimpMessagingTemplate
) {
    /**
     * 메시지 전송
     */
    @Transactional
    fun sendMessage(senderId: String, chatRoomId: String, request: MessageRequest) {
        // [1] ChatMessage 저장
        val chatMessage = ChatMessage.fromChatMessageRequest(
            chatRoomId = chatRoomId, senderId = senderId, request = request
        )
        mapper.insertChatMessage(chatMessage = chatMessage)

        // [2] /sub/chat/${chatRoomId}/message를 구독중인 사람들에게 메시지 전달
        messageTemplate.convertAndSend("/sub/chat/$chatRoomId/message", chatMessage)
    }
}