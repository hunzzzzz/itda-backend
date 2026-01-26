package com.moira.itda.domain.chat_room.mapper

import com.moira.itda.domain.chat_room.dto.response.ChatMessageResponse
import com.moira.itda.domain.chat_room.dto.response.ChatRoomDetailResponse
import com.moira.itda.global.entity.ChatMessage
import com.moira.itda.global.entity.TradeCancelHistory
import com.moira.itda.global.entity.TradeCompleteHistory
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ChatRoomMapper {
    /**
     * 내 활동 > 채팅 > 채팅방 > 거래제안 정보 조회
     */
    fun selectTradeSuggest(chatRoomId: String): ChatRoomDetailResponse

    /**
     * 내 활동 > 채팅 > 채팅방 > 이전 채팅 목록 조회
     */
    fun selectChatMessageList(chatRoomId: String): List<ChatMessageResponse>

    /**
     * 내 활동 > 채팅 > 채팅방 > 메시지 전송 > ChatMessage 저장
     */
    fun insertChatMessage(chatMessage: ChatMessage)

    /**
     * 거래취소 > 채팅방 정보 조회
     */
    fun selectChatRoomInfo(chatRoomId: String): HashMap<String, String?>

    /**
     * 거래취소 > TradeCancelHistory 저장
     */
    fun insertTradeCancelHistory(tradeCancelHistory: TradeCancelHistory)

    /**
     * 거래취소 > ChatRoom status 변경 (ENDED)
     * 거래완료 > ChatRoom status 변경 (ENDED)
     */
    fun updateChatRoomStatusEnded(chatRoomId: String)

    /**
     * 거래취소 > TradeSuggest status 변경 (CANCELED)
     */
    fun updateTradeSuggestStatusCanceled(suggestId: String)

    /**
     * 내 활동 > 채팅 > 채팅방 > 거래완료 > TradeCompleteHistory 저장
     */
    fun insertTradeCompleteHistory(tradeCompleteHistory: TradeCompleteHistory)

    /**
     * 내 활동 > 채팅 > 채팅방 > 거래완료 > TradeSuggest status 변경 (COMPLETED)
     */
    fun updateTradeSuggestStatusCompleted(tradeSuggestId: String)

    /**
     * 내 활동 > 채팅 > 채팅방 > 거래완료 > 나머지 TradeSuggest의 status 변경 (REJECTED)
     */
    fun updateRestTradeSuggestStatusRejected(tradeItemId: String, tradeSuggestId: String)

    /**
     * 내 활동 > 채팅 > 채팅방 > 거래완료 > TradeItem status 변경 (COMPLETED)
     */
    fun updateTradeItemStatusCompleted(tradeItemId: String)

    /**
     * 내 활동 > 채팅 > 채팅방 > 거래완료 > Trade 하위에 status가 PENDING인 TradeItem 존재 여부 확인
     */
    fun selectTradeItemStatusPendingChk(tradeId: String): Boolean

    /**
     * 내 활동 > 채팅 > 채팅방 > 거래완료 > Trade status 변경 (COMPLETED)
     */
    fun updateTradeStatusCompleted(tradeId: String)
}