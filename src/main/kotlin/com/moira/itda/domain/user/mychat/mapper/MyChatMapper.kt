package com.moira.itda.domain.user.mychat.mapper

import com.moira.itda.domain.user.mychat.dto.response.MyChatResponse
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MyChatMapper {
    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > totalElements 계산
     */
    fun selectChatRoomListCnt(userId: String): Long

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회
     */
    fun selectChatRoomList(userId: String, pageSize: Int, offset: Int): List<MyChatResponse>
}