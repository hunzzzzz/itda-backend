package com.moira.itda.domain.chat.detail.mapper

import com.moira.itda.domain.chat.detail.dto.response.ChatRoomDetailResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ChatRoomDetailMapper {
    /**
     * 채팅방 상단정보 조회
     */
    fun selectChatRoomDetailResponse(chatRoomId: String): ChatRoomDetailResponse
}