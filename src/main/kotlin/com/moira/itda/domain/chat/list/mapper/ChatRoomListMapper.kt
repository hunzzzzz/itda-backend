package com.moira.itda.domain.chat.list.mapper

import com.moira.itda.domain.chat.list.dto.response.ChatRoomResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ChatRoomListMapper {
    /**
     * 내 채팅방 목록 조회 > totalElements 계산
     */
    fun selectChatRoomListCnt(userId: String): Long

    /**
     * 내 채팅방 목록 조회
     */
    fun selectChatRoomList(
        userId: String,
        pageSize: Int,
        offset: Int
    ): List<ChatRoomResponse>
}