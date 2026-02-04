package com.moira.itda.domain.chat.cancel.service

import com.moira.itda.domain.chat.cancel.dto.request.CancelRequest
import com.moira.itda.domain.chat.cancel.mapper.ChatRoomCancelMapper
import com.moira.itda.domain.notification.component.NotificationManager
import com.moira.itda.global.entity.ChatStatus
import com.moira.itda.global.entity.TradeCancelHistory
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomCancelService(
    private val mapper: ChatRoomCancelMapper,
    private val notificationManager: NotificationManager
) {
    /**
     * 거래취소
     */
    @Transactional
    fun cancel(userId: String, chatRoomId: String, request: CancelRequest) {
        // [1] 채팅방 정보 조회
        val infoMap = mapper.selectChatRoomInfo(chatRoomId = chatRoomId)
        val status = infoMap["status"]
        val sellerId = infoMap["seller_id"]
        val buyerId = infoMap["buyer_id"]

        // [2] 유효성 검사
        if (status == null || sellerId == null || buyerId == null || (sellerId != userId && buyerId != userId)) {
            throw ItdaException(ErrorCode.FORBIDDEN)
        }
        if (status == ChatStatus.ENDED.name) {
            throw ItdaException(ErrorCode.ALREADY_ENDED_CHATROOM)
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
}