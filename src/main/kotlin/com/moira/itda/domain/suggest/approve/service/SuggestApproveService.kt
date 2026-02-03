package com.moira.itda.domain.suggest.approve.service

import com.moira.itda.domain.notification.component.NotificationManager
import com.moira.itda.domain.suggest.approve.dto.request.ApproveRequest
import com.moira.itda.domain.suggest.approve.dto.response.ChatRoomIdResponse
import com.moira.itda.domain.suggest.approve.mapper.SuggestApproveMapper
import com.moira.itda.global.entity.ChatRoom
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SuggestApproveService(
    private val notificationManager: NotificationManager,
    private val mapper: SuggestApproveMapper,
) {
    /**
     * 제안승인
     */
    @Transactional
    fun approve(userId: String, suggestId: String, request: ApproveRequest): ChatRoomIdResponse {
        // [1] 변수 세팅
        val (tradeId, tradeItemId, buyerId) = request

        // [2] TradeSuggest의 상태값을 APPROVED로 변경
        mapper.updateTradeSuggestStatusApproved(suggestId = suggestId)

        // [3] ChatRoom 저장
        val chatRoom = ChatRoom.from(
            tradeId = tradeId,
            tradeItemId = tradeItemId,
            tradeSuggestId = suggestId,
            sellerId = userId,
            buyerId = buyerId
        )
        mapper.insertChatRoom(chatRoom = chatRoom)

        // [4] 알림 전송 (비동기)
        notificationManager.sendSuggestApproveNotification(
            senderId = userId,
            suggestId = suggestId,
            chatRoomId = chatRoom.id
        )

        // [5] 채팅방 ID 리턴
        return ChatRoomIdResponse(chatRoomId = chatRoom.id)
    }
}