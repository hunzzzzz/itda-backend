package com.moira.itda.domain.chat.detail.service

import com.moira.itda.domain.chat.detail.dto.response.ChatRoomDetailResponse
import com.moira.itda.domain.chat.detail.mapper.ChatRoomDetailMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomDetailService(
    private val mapper: ChatRoomDetailMapper
) {
    /**
     * 채팅방 상단정보 조회
     */
    @Transactional(readOnly = true)
    fun getChatRoomDetail(chatRoomId: String): ChatRoomDetailResponse {
        return mapper.selectChatRoomDetailResponse(chatRoomId = chatRoomId)
    }
}