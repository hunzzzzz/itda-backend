package com.moira.itda.domain.user_temp.mychat.mapper

import com.moira.itda.domain.user_temp.mychat.dto.response.ChatMessageResponse
import com.moira.itda.domain.user_temp.mychat.dto.response.ChatRoomResponse
import com.moira.itda.domain.user_temp.mychat.dto.response.MyChatResponse
import com.moira.itda.global.entity.ChatMessage
import com.moira.itda.global.entity.TradeCancelHistory
import com.moira.itda.global.entity.TradeCompleteHistory
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
    fun selectTradeSuggest(chatRoomId: String): ChatRoomResponse

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 이전 채팅 목록 조회
     */
    fun selectChatMessageList(chatRoomId: String): List<ChatMessageResponse>

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 메시지 전송 > ChatMessage 저장
     */
    fun insertChatMessage(chatMessage: ChatMessage)

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 거래 완료 > 채팅방 status 조회
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 거래 취소 > 채팅방 status 조회
     */
    fun selectChatStatus(chatRoomId: String): String?

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 거래 완료 > TradeCompleteHistory 저장
     */
    fun insertTradeCompleteHistory(tradeCompleteHistory: TradeCompleteHistory)

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 거래 완료 > Trade의 status 변경
     */
    fun updateTradeStatusCompleted(tradeId: String)

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 거래 완료 > TradeItem의 status 변경
     */
    fun updateTradeItemStatusCompleted(tradeItemId: String)

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 거래 완료 > ChatRoom의 status 변경
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 거래 취소 > ChatRoom의 status 변경
     */
    fun updateChatRoomStatusEnded(chatRoomId: String)

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 거래 취소 > TradeCancelHistory 저장
     */
    fun insertTradeCancelHistory(tradeCancelHistory: TradeCancelHistory)

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 거래 취소 > TradeSuggest의 status 변경
     */
    fun updateTradeSuggestStatusCanceled(tradeSuggestId: String)
}