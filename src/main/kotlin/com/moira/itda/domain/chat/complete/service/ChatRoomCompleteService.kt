package com.moira.itda.domain.chat.complete.service

import com.moira.itda.domain.chat.cancel.dto.request.CancelRequest
import com.moira.itda.domain.chat.cancel.service.ChatRoomCancelService
import com.moira.itda.domain.chat.complete.dto.request.CompleteRequest
import com.moira.itda.domain.chat.complete.mapper.ChatRoomCompleteMapper
import com.moira.itda.domain.notification.component.NotificationManager
import com.moira.itda.domain.suggest.reject.service.SuggestRejectService
import com.moira.itda.global.entity.ChatStatus
import com.moira.itda.global.entity.TradeCompleteHistory
import com.moira.itda.global.entity.TradeSuggestStatus
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomCompleteService(
    private val cancelService: ChatRoomCancelService,
    private val mapper: ChatRoomCompleteMapper,
    private val notificationManager: NotificationManager,
    private val rejectService: SuggestRejectService,
) {
    /**
     * 거래완료
     */
    @Transactional
    fun completeTrade(userId: String, chatRoomId: String, request: CompleteRequest) {
        // [1] 채팅 정보 조회
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

        // [3] TradeCompleHistory 저장
        val tradeCompleteHistory = TradeCompleteHistory.fromTradeCompleteRequest(
            chatRoomId = chatRoomId, request = request, sellerId = sellerId, buyerId = buyerId
        )
        mapper.insertTradeCompleteHistory(tradeCompleteHistory = tradeCompleteHistory)

        // [4] ChatRoom의 status 변경 (ENDED)
        mapper.updateChatRoomStatusEnded(chatRoomId = chatRoomId)

        // [5] TradeSuggest의 status 변경 (COMPLETED)
        mapper.updateTradeSuggestStatusCompleted(tradeSuggestId = request.tradeSuggestId)

        // [6] 해당 TradeItem에 걸려 있는 나머지 TradeSuggest에 대한 거절 및 취소 처리
        /*
            나머지 제안이
            1) PENDING인 경우  -> REJECT 처리
            2) APPROVED인 경우 -> CANCELED 처리
            3) CANCELED_BEFORE_RESPONSE, REJECTED, CANCELED, DELETED인 경우 -> 고려대상 X
         */
        val restSuggestList = mapper.selectRestTradeSuggestList(
            tradeItemId = request.tradeItemId,
            completedSuggestId = request.tradeSuggestId
        )
        for (infoMap in restSuggestList) {
            val iSuggestId = infoMap["suggest_id"]
            val iStatus = infoMap["status"]
            val iChatRoomId = infoMap["chat_room_id"]
            val iTradeId = infoMap["trade_id"]
            val iGachaId = infoMap["gacha_id"]

            if (iSuggestId == null || iStatus == null || iChatRoomId == null || iTradeId == null || iGachaId == null) {
                throw ItdaException(ErrorCode.FORBIDDEN)
            }

            when (iStatus) {
                TradeSuggestStatus.PENDING.name -> {
                    rejectService.reject(userId = userId, suggestId = iSuggestId)
                }

                TradeSuggestStatus.APPROVED.name -> {
                    cancelService.cancel(
                        userId = userId,
                        chatRoomId = iChatRoomId,
                        request = CancelRequest(
                            tradeId = iTradeId,
                            tradeSuggestId = iSuggestId,
                            gachaId = iGachaId,
                            cancelReason = "판매자가 해당 상품의 거래를 완료하였습니다."
                        )
                    )
                }

                else -> throw ItdaException(ErrorCode.FORBIDDEN)
            }
        }

        // [7] TradeItem의 status 변경 (COMPLETED)
        mapper.updateTradeItemStatusCompleted(tradeItemId = request.tradeItemId)

        // [8] Trade 하위에 PENDING인 TradeItem이 없으면 Trade의 status 변경 (COMPLETED)
        if (!mapper.selectTradeItemStatusPendingChk(tradeId = request.tradeId)) {
            mapper.updateTradeStatusCompleted(tradeId = request.tradeId)
        }

        // [9] 알림 전송 (비동기)
        notificationManager.sendTradeCompleteNotification(senderId = userId, chatRoomId = chatRoomId)
    }
}