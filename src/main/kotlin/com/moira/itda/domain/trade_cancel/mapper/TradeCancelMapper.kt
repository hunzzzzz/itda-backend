package com.moira.itda.domain.trade_cancel.mapper

import com.moira.itda.global.entity.TradeCancelHistory
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TradeCancelMapper {
    /**
     * 거래취소 > 채팅방 정보 조회
     */
    fun selectChatInfo(chatRoomId: String): HashMap<String, String?>

    /**
     * 거래취소 > TradeCancelHistory 저장
     */
    fun insertTradeCancelHistory(tradeCancelHistory: TradeCancelHistory)

    /**
     * 거래취소 > ChatRoom의 status 변경 (ENDED)
     */
    fun updateChatRoomStatusEnded(chatRoomId: String)

    /**
     * 거래취소 > TradeSuggest의 status 변경 (CANCELED)
     */
    fun updateTradeSuggestStatusCanceled(tradeSuggestId: String)
}