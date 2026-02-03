package com.moira.itda.domain.chat.history.mapper

import com.moira.itda.domain.chat.history.dto.response.ChatMessageResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ChatHistoryMapper {
    /**
     * 이전 채팅내역 조회
     */
    fun selectChatMessageList(chatRoomId: String): List<ChatMessageResponse>
}