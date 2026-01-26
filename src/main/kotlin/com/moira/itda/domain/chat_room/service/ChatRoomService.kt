package com.moira.itda.domain.chat_room.service

import com.moira.itda.domain.chat_room.component.ChatValidator
import com.moira.itda.domain.chat_room.dto.request.ChatMessageRequest
import com.moira.itda.domain.chat_room.dto.request.ChatRoomTradeCancelRequest
import com.moira.itda.domain.chat_room.dto.request.TradeCompleteRequest
import com.moira.itda.domain.chat_room.dto.response.ChatMessageResponse
import com.moira.itda.domain.chat_room.dto.response.ChatRoomDetailResponse
import com.moira.itda.domain.chat_room.mapper.ChatRoomMapper
import com.moira.itda.domain.notification.component.NotificationManager
import com.moira.itda.global.entity.ChatMessage
import com.moira.itda.global.entity.ChatStatus
import com.moira.itda.global.entity.TradeCancelHistory
import com.moira.itda.global.entity.TradeCompleteHistory
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomService(
    private val mapper: ChatRoomMapper,
    private val messageTemplate: SimpMessagingTemplate,
    private val notificationManager: NotificationManager,
    private val validator: ChatValidator
) {
    /**
     * 내 활동 > 채팅 > 채팅방 > 거래제안 정보 조회
     */
    @Transactional(readOnly = true)
    fun getTradeSuggest(chatRoomId: String): ChatRoomDetailResponse {
        return mapper.selectTradeSuggest(chatRoomId = chatRoomId)
    }

    /**
     * 내 활동 > 채팅 > 채팅방 > 이전 채팅 목록 조회
     */
    @Transactional(readOnly = true)
    fun getChatMessageList(chatRoomId: String): List<ChatMessageResponse> {
        return mapper.selectChatMessageList(chatRoomId = chatRoomId)
    }

    /**
     * 내 활동 > 채팅 > 채팅방 > 메시지 전송
     */
    @Transactional
    fun sendMessage(senderId: String, chatRoomId: String, request: ChatMessageRequest) {
        // [1] ChatMessage 저장
        val chatMessage = ChatMessage.fromChatMessageRequest(
            chatRoomId = chatRoomId, senderId = senderId, request = request
        )
        mapper.insertChatMessage(chatMessage = chatMessage)

        // [2] /sub/chat/${chatRoomId}/message를 구독중인 사람들에게 메시지 전달
        messageTemplate.convertAndSend("/sub/chat/$chatRoomId/message", chatMessage)
    }

    /**
     * 거래취소
     */
    @Transactional
    fun cancelTrade(userId: String, chatRoomId: String, request: ChatRoomTradeCancelRequest) {
        // [1] 채팅 정보 조회
        val infoMap = mapper.selectChatRoomInfo(chatRoomId = chatRoomId)
        val status = infoMap["status"]
        val sellerId = infoMap["seller_id"]
        val buyerId = infoMap["buyer_id"]

        if (status == null || sellerId == null || buyerId == null) {
            throw ItdaException(ErrorCode.CHAT_NOT_FOUND)
        }

        // [2] 유효성 검사
        if (status == ChatStatus.ENDED.name) {
            throw ItdaException(ErrorCode.ALREADY_ENDED_CHAT)
        }
        if (sellerId != userId && buyerId != userId) {
            throw ItdaException(ErrorCode.OTHERS_CHAT)
        }

        // [3] TradeCancelHistory 저장
        val tradeCancelHistory = TradeCancelHistory.from(userId = userId, chatRoomId = chatRoomId, request = request)
        mapper.insertTradeCancelHistory(tradeCancelHistory = tradeCancelHistory)

        // [4] ChatRoom의 status 변경 (ENDED)
        mapper.updateChatRoomStatusEnded(chatRoomId = chatRoomId)

        // [5] TradeSuggest의 status 변경 (CANCELED)
        mapper.updateTradeSuggestStatusCanceled(suggestId = request.tradeSuggestId)

        // [6] 알림 전송 (비동기)
        notificationManager.sendTradeCancelNotification(senderId = userId, chatRoomId = chatRoomId)
    }

    /**
     * 내 활동 > 채팅 > 채팅방 > 거래완료
     */
    @Transactional
    fun completeTrade(userId: String, chatRoomId: String, request: TradeCompleteRequest) {
        // [1] 채팅 정보 조회
        val infoMap = mapper.selectChatRoomInfo(chatRoomId = chatRoomId)

        val (status, sellerId, buyerId) = infoMap.values.map {
            it ?: throw ItdaException(ErrorCode.CHAT_NOT_FOUND)
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