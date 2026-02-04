package com.moira.itda.domain.chat.complete.mapper

import com.moira.itda.global.entity.TradeCompleteHistory
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ChatRoomCompleteMapper {
    /**
     * 거래완료 > 채팅방 정보 조회
     */
    fun selectChatRoomInfo(chatRoomId: String): HashMap<String, String?>

    /**
     * 거래완료 > TradeCompleteHistory 저장
     */
    fun insertTradeCompleteHistory(tradeCompleteHistory: TradeCompleteHistory)

    /**
     * 거래완료 > ChatRoom의 status 변경 (ENDED)
     */
    fun updateChatRoomStatusEnded(chatRoomId: String)

    /**
     * 거래완료 > TradeSuggest의 status 변경 (COMPLETED)
     */
    fun updateTradeSuggestStatusCompleted(tradeSuggestId: String)

    /**
     * 거래완료 > 나머지 TradeSuggest 목록 조회
     */
    fun selectRestTradeSuggestList(tradeItemId: String, completedSuggestId: String): List<HashMap<String, String?>>

    /**
     * 거래완료 > TradeItem의 status 변경 (COMPLETED)
     */
    fun updateTradeItemStatusCompleted(tradeItemId: String)

    /**
     * 거래완료 > Trade 하위에 status가 PENDING인 TradeItem 존재 여부 확인
     */
    fun selectTradeItemStatusPendingChk(tradeId: String): Boolean

    /**
     * 거래완료 > Trade의 status 변경 (COMPLETED)
     */
    fun updateTradeStatusCompleted(tradeId: String)
}