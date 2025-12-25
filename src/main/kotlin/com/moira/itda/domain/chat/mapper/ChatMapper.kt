package com.moira.itda.domain.chat.mapper

import com.moira.itda.global.entity.ChatMessage
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ChatMapper {
    /**
     * 채팅방 > 메시지 전송 > ChatMessage 저장
     */
    fun insertChatMessage(chatMessage: ChatMessage)
}