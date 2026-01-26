package com.moira.itda.domain.suggest_list.service

import com.moira.itda.domain.notification.component.NotificationManager
import com.moira.itda.domain.suggest_list.dto.request.SuggestYnRequest
import com.moira.itda.domain.suggest_list.dto.response.ChatRoomIdResponse
import com.moira.itda.domain.suggest_list.dto.response.SuggestListPageResponse
import com.moira.itda.domain.suggest_list.mapper.SuggestListMapper
import com.moira.itda.global.entity.ChatRoom
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.TRADE_SUGGEST_MODAL_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SuggestListService(
    private val notificationManager: NotificationManager,
    private val mapper: SuggestListMapper,
    private val pageHandler: OffsetPaginationHandler
) {
    /**
     * 제안목록 조회
     */
    @Transactional(readOnly = true)
    fun getSuggestList(userId: String, tradeId: String, page: Int): SuggestListPageResponse {
        // [1] 변수 세팅
        val pageSize = TRADE_SUGGEST_MODAL_LIST_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 제안목록 조회
        val totalElements = mapper.selectTradeSuggestListCnt(tradeId = tradeId)
        val content = mapper.selectTradeSuggestList(
            userId = userId,
            tradeId = tradeId,
            pageSize = pageSize,
            offset = offset
        )

        // [3] 오프셋 페이지네이션 적용
        val pageResponse = pageHandler.getPageResponse(
            pageSize = pageSize,
            page = page,
            totalElements = totalElements
        )

        // [4] DTO 병합 후 리턴
        return SuggestListPageResponse(content = content, page = pageResponse)
    }

    /**
     * 제안승인
     */
    @Transactional
    fun approve(userId: String, tradeId: String, request: SuggestYnRequest): ChatRoomIdResponse {
        // [1] 변수 세팅
        val (tradeItemId, tradeSuggestId, buyerId) = request

        // [2] TradeSuggest의 상태값을 APPROVED로 변경
        mapper.updateTradeSuggestStatusApproved(suggestId = tradeSuggestId)

        // [3] ChatRoom 저장
        val chatRoom = ChatRoom.from(
            tradeId = tradeId,
            tradeItemId = tradeItemId,
            tradeSuggestId = tradeSuggestId,
            sellerId = userId,
            buyerId = buyerId
        )
        mapper.insertChatRoom(chatRoom = chatRoom)

        // [4] 알림 전송 (비동기)
        notificationManager.sendSuggestApproveNotification(
            senderId = userId,
            suggestId = tradeSuggestId,
            chatRoomId = chatRoom.id
        )

        // [5] 채팅방 ID 리턴
        return ChatRoomIdResponse(chatRoomId = chatRoom.id)
    }

    /**
     * 제안거절
     */
    @Transactional
    fun reject(userId: String, tradeId: String, request: SuggestYnRequest) {
        // [1] 변수 세팅
        val suggestId = request.tradeSuggestId

        // [2] TradeSuggest의 상태값을 REJECTED로 변경
        mapper.updateTradeSuggestStatusRejected(suggestId = suggestId)

        // [3] 알림 전송 (비동기)
        notificationManager.sendSuggestRejectedNotification(senderId = userId, suggestId = suggestId)
    }
}