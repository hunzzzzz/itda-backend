package com.moira.itda.domain.chat.temp.mapper

import com.moira.itda.global.entity.TradeCompleteHistory
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ChatRoomMapper {
    /**
     * 거래취소 > 채팅방 정보 조회
     */
    fun selectChatRoomInfo(chatRoomId: String): HashMap<String, String?>

    /**
     * 거래취소 > ChatRoom status 변경 (ENDED)
     * 거래완료 > ChatRoom status 변경 (ENDED)
     */
    fun updateChatRoomStatusEnded(chatRoomId: String)

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