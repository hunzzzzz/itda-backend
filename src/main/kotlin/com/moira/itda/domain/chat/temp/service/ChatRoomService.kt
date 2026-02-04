package com.moira.itda.domain.chat.temp.service

import com.moira.itda.domain.chat.temp.component.ChatValidator
import com.moira.itda.domain.chat.temp.dto.request.TradeCompleteRequest
import com.moira.itda.domain.chat.temp.mapper.ChatRoomMapper
import com.moira.itda.global.entity.TradeCompleteHistory
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomService(
    private val mapper: ChatRoomMapper,
    private val validator: ChatValidator
) {
    /**
     * 내 활동 > 채팅 > 채팅방 > 거래완료
     */
    @Transactional
    fun completeTrade(userId: String, chatRoomId: String, request: TradeCompleteRequest) {
        // [1] 채팅 정보 조회
        val infoMap = mapper.selectChatRoomInfo(chatRoomId = chatRoomId)

        val (status, sellerId, buyerId) = infoMap.values.map {
            it ?: throw ItdaException(ErrorCode.FORBIDDEN)
        }

        // [2] 유효성 검사
        validator.validate(userId = userId, status = status, sellerId = sellerId, buyerId = buyerId)

        // [3] TradeCompleHistory 저장
        val tradeCompleteHistory = TradeCompleteHistory.fromTradeCompleteRequest(
            chatRoomId = chatRoomId, request = request
        )
        mapper.insertTradeCompleteHistory(tradeCompleteHistory = tradeCompleteHistory)

        // [4] ChatRoom의 status 변경 (ENDED)
        mapper.updateChatRoomStatusEnded(chatRoomId = chatRoomId)

        // [5] TradeSuggest의 status 변경 (COMPLETED)
        mapper.updateTradeSuggestStatusCompleted(tradeSuggestId = request.tradeSuggestId)

        // [6] 해당 TradeItem에 걸려 있는 나머지 TradeSuggest의 status 변경 (CANCELED)
        mapper.updateRestTradeSuggestStatusRejected(
            tradeItemId = request.tradeItemId,
            tradeSuggestId = request.tradeSuggestId
        )

        // [7] TradeItem의 status 변경 (COMPLETED)
        mapper.updateTradeItemStatusCompleted(tradeItemId = request.tradeItemId)

        // [8] Trade 하위에 PENDING인 TradeItem이 없으면 Trade의 status 변경 (COMPLETED)
        if (!mapper.selectTradeItemStatusPendingChk(tradeId = request.tradeId)) {
            mapper.updateTradeStatusCompleted(tradeId = request.tradeId)
        }
    }
}