package com.moira.itda.domain.chat.mapper

import com.moira.itda.domain.chat.dto.response.ChatMessageResponse
import com.moira.itda.domain.chat.dto.response.ChatRoomResponse
import com.moira.itda.domain.chat.dto.response.MyChatResponse
import com.moira.itda.global.entity.ChatMessage
import com.moira.itda.global.entity.TradeCancelHistory
import com.moira.itda.global.entity.TradeCompleteHistory
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ChatMapper {
    /**
     * 내 활동 > 채팅 > 채팅방 목록 조회 > totalElements 계산
     */
    fun selectChatRoomListCnt(userId: String): Long

    /**
     * 내 활동 > 채팅 > 채팅방 목록 조회
     */
    fun selectChatRoomList(userId: String, pageSize: Int, offset: Int): List<MyChatResponse>

    /**
     * 내 활동 > 채팅 > 채팅방 > 거래제안 정보 조회
     */
    fun selectTradeSuggest(chatRoomId: String): ChatRoomResponse

    /**
     * 내 활동 > 채팅 > 채팅방 > 이전 채팅 목록 조회
     */
    fun selectChatMessageList(chatRoomId: String): List<ChatMessageResponse>

    /**
     * 내 활동 > 채팅 > 채팅방 > 메시지 전송 > ChatMessage 저장
     */
    fun insertChatMessage(chatMessage: ChatMessage)

    /**
     * 내 활동 > 채팅 > 채팅방 > 거래취소 > 채팅방 정보 조회
     */
    fun selectChatInfo(chatRoomId: String): HashMap<String, String?>

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
     * 내 활동 > 채팅 > 채팅방 > 거래취소 > ChatRoom status 변경 (ENDED)
     */
    fun updateChatRoomStatusEnded(chatRoomId: String)

    /**
     * 내 활동 > 채팅 > 채팅방 > 거래취소 > > TradeCancelHistory 저장
     */
    fun insertTradeCancelHistory(tradeCancelHistory: TradeCancelHistory)

    /**
     * 내 활동 > 채팅 > 채팅방 > 거래취소 > TradeSuggest의 status 변경 (CANCELED)
     */
    fun updateTradeSuggestStatusCanceled(tradeSuggestId: String)
}