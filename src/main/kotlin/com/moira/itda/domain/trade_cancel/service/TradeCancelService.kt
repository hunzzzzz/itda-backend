package com.moira.itda.domain.trade_cancel.service

import com.moira.itda.domain.trade_cancel.dto.request.TradeCancelRequest
import com.moira.itda.domain.trade_cancel.mapper.TradeCancelMapper
import com.moira.itda.global.entity.ChatStatus
import com.moira.itda.global.entity.TradeCancelHistory
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeCancelService(
    private val mapper: TradeCancelMapper
) {
    /**
     * 거래취소 > 유효성 검사
     */
    private fun validate(
        userId: String,
        status: String,
        sellerId: String,
        buyerId: String,
        request: TradeCancelRequest
    ) {
        // [1] status 검증
        if (status == ChatStatus.ENDED.name) {
            throw ItdaException(ErrorCode.ALREADY_ENDED_CHAT)
        }

        // [2] 권한 검증
        if (userId != sellerId && userId != buyerId) {
            throw ItdaException(ErrorCode.OTHERS_CHAT)
        }

        // [3] 취소 사유 검증
        if (request.cancelReason.isBlank()) {
            throw ItdaException(ErrorCode.NO_CANCEL_REASON)
        }
    }

    /**
     * 거래취소
     */
    @Transactional
    fun cancelTrade(userId: String, chatRoomId: String, request: TradeCancelRequest) {
        // [1] 채팅 정보 조회
        val infoMap = mapper.selectChatInfo(chatRoomId = chatRoomId)

        val (status, sellerId, buyerId) = infoMap.values.map {
            it ?: throw ItdaException(ErrorCode.CHAT_NOT_FOUND)
        }

        // [2] 유효성 검사
        this.validate(status = status, userId = userId, sellerId = sellerId, buyerId = buyerId, request = request)

        // [3] TradeCancelHistory 저장
        val tradeCancelHistory = TradeCancelHistory.fromTradeCancelRequest(chatRoomId = chatRoomId, request = request)
        mapper.insertTradeCancelHistory(tradeCancelHistory = tradeCancelHistory)

        // [4] ChatRoom의 status 변경 (ENDED)
        mapper.updateChatRoomStatusEnded(chatRoomId = chatRoomId)

        // [5] TradeSuggest의 status 변경 (CANCELED)
        mapper.updateTradeSuggestStatusCanceled(tradeSuggestId = request.tradeSuggestId)
    }
}