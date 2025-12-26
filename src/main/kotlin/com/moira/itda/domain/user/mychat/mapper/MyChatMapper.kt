package com.moira.itda.domain.user.mychat.mapper

import com.moira.itda.domain.user.mychat.dto.response.ChatMessageResponse
import com.moira.itda.domain.user.mychat.dto.response.MyChatResponse
import com.moira.itda.domain.user.mychat.dto.response.TradeSuggestResponse
import com.moira.itda.global.entity.ChatMessage
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

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 거래 제안 정보 조회
     */
    fun selectTradeSuggest(chatRoomId: String): TradeSuggestResponse

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 이전 채팅 목록 조회
     */
    fun selectChatMessageList(chatRoomId: String): List<ChatMessageResponse>

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 메시지 전송 > ChatMessage 저장
     */
    fun insertChatMessage(chatMessage: ChatMessage)

    /**
     *
     */
    fun selectChatStatus(chatRoomId: String): String?
}