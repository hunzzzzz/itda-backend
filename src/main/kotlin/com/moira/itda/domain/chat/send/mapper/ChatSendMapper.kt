package com.moira.itda.domain.chat.send.mapper

import com.moira.itda.global.entity.ChatMessage
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ChatSendMapper {
    /**
     * 메시지 전송 > ChatMessage 저장
     */
    fun insertChatMessage(chatMessage: ChatMessage)
}