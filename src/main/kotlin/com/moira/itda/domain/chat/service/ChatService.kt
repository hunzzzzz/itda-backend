package com.moira.itda.domain.chat.service

import com.moira.itda.domain.chat.dto.request.ChatMessageRequest
import com.moira.itda.domain.chat.dto.request.TradeCancelRequest
import com.moira.itda.domain.chat.dto.request.TradeCompleteRequest
import com.moira.itda.domain.chat.dto.response.ChatMessageResponse
import com.moira.itda.domain.chat.dto.response.ChatRoomResponse
import com.moira.itda.domain.chat.dto.response.MyChatPageResponse
import com.moira.itda.domain.chat.mapper.ChatMapper
import com.moira.itda.global.entity.ChatMessage
import com.moira.itda.global.entity.ChatStatus
import com.moira.itda.global.entity.TradeCancelHistory
import com.moira.itda.global.entity.TradeCompleteHistory
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_TRADE_CHAT_LIST_PAGE_SIZE
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatService(
    private val chatMapper: ChatMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler,
    private val messageTemplate: SimpMessagingTemplate
) {
    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회
     */
    @Transactional(readOnly = true)
    fun getChatList(userId: String, page: Int): MyChatPageResponse {
        // [1] 변수 세팅
        val pageSize = MY_TRADE_CHAT_LIST_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 채팅방 정보 조회
        val totalElements = chatMapper.selectChatRoomListCnt(userId = userId)
        val content = chatMapper.selectChatRoomList(
            userId = userId, pageSize = pageSize, offset = offset
        )

        // [3] 오프셋 페이지네이션 구현
        val pageResponse = offsetPaginationHandler.getPageResponse(
            page = page, pageSize = pageSize, totalElements = totalElements
        )

        // [4] DTO 병합 후 리턴
        return MyChatPageResponse(content = content, page = pageResponse)
    }

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 거래제안 정보 조회
     */
    @Transactional(readOnly = true)
    fun getTradeSuggest(chatRoomId: String): ChatRoomResponse {
        return chatMapper.selectTradeSuggest(chatRoomId = chatRoomId)
    }

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 이전 채팅 목록 조회
     */
    @Transactional(readOnly = true)
    fun getChatMessageList(chatRoomId: String): List<ChatMessageResponse> {
        return chatMapper.selectChatMessageList(chatRoomId = chatRoomId)
    }

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 메시지 전송
     */
    @Transactional
    fun sendMessage(chatRoomId: String, request: ChatMessageRequest) {
        // [1] ChatMessage 저장
        val chatMessage = ChatMessage.fromChatMessageRequest(chatRoomId = chatRoomId, request = request)
        chatMapper.insertChatMessage(chatMessage = chatMessage)

        // [2] /sub/chat/${chatRoomId}/message를 구독중인 사람들에게 메시지 전달
        messageTemplate.convertAndSend("/sub/chat/$chatRoomId/message", chatMessage)
    }

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 거래 완료
     */
    @Transactional
    fun completeTrade(chatRoomId: String, request: TradeCompleteRequest) {
        // [1] 유효성 검사 (status)
        val status = chatMapper.selectChatStatus(chatRoomId = chatRoomId)
            ?: throw ItdaException(ErrorCode.INVALID_CHAT_STATUS)

        if (status == ChatStatus.ENDED.name) {
            throw ItdaException(ErrorCode.ALREADY_ENDED_CHAT)
        }

        // [2] TradeCompleHistory 저장
        val tradeCompleteHistory = TradeCompleteHistory.fromTradeCompleteRequest(
            chatRoomId = chatRoomId, request = request
        )
        chatMapper.insertTradeCompleteHistory(tradeCompleteHistory = tradeCompleteHistory)

        // [3] ChatRoom의 status 변경 (ENDED)
        chatMapper.updateChatRoomStatusEnded(chatRoomId = chatRoomId)

        // [4] Trade의 status 변경 (COMPLETED)
        chatMapper.updateTradeStatusCompleted(tradeId = request.tradeId)
        chatMapper.updateTradeItemStatusCompleted(tradeItemId = request.tradeItemId)
    }

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 거래 취소
     */
    @Transactional
    fun cancelTrade(chatRoomId: String, request: TradeCancelRequest) {
        // [1] 유효성 검사 (status)
        val status = chatMapper.selectChatStatus(chatRoomId = chatRoomId)
            ?: throw ItdaException(ErrorCode.INVALID_CHAT_STATUS)

        if (status == ChatStatus.ENDED.name) {
            throw ItdaException(ErrorCode.ALREADY_ENDED_CHAT)
        }

        // [2] TradeCancelHistory 저장
        val tradeCancelHistory = TradeCancelHistory.fromTradeCancelRequest(chatRoomId = chatRoomId, request = request)
        chatMapper.insertTradeCancelHistory(tradeCancelHistory = tradeCancelHistory)

        // [3] ChatRoom의 status 변경 (ENDED)
        chatMapper.updateChatRoomStatusEnded(chatRoomId = chatRoomId)

        // [4] TradeSuggest의 status 변경 (CANCELED)
        chatMapper.updateTradeSuggestStatusCanceled(tradeSuggestId = request.tradeSuggestId)
    }
}