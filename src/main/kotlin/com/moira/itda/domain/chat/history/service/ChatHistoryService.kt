package com.moira.itda.domain.chat.history.service

import com.moira.itda.domain.chat.history.dto.response.ChatMessageResponse
import com.moira.itda.domain.chat.history.mapper.ChatHistoryMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatHistoryService(
    private val mapper: ChatHistoryMapper
) {
    /**
     * 이전 채팅내역 조회
     */
    @Transactional(readOnly = true)
    fun getChatMessageList(chatRoomId: String): List<ChatMessageResponse> {
        return mapper.selectChatMessageList(chatRoomId = chatRoomId)
    }
}