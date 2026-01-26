package com.moira.itda.domain.user_activity_chat.mapper

import com.moira.itda.domain.user_activity_chat.dto.response.ChatRoomResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserActivityChatRoomMapper {
    /**
     * 채팅방 목록 조회 > totalElements 계산
     */
    fun selectChatRoomListCnt(userId: String): Long

    /**
     * 채팅방 목록 조회
     */
    fun selectChatRoomList(
        userId: String,
        pageSize: Int,
        offset: Int
    ): List<ChatRoomResponse>
}