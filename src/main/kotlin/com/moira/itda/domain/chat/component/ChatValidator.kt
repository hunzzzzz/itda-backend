package com.moira.itda.domain.chat.component

import com.moira.itda.domain.chat.dto.request.TradeCancelRequest
import com.moira.itda.global.entity.ChatStatus
import com.moira.itda.global.entity.TradeCancelReason
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Component

@Component
class ChatValidator {
    /**
     * 거래완료 > 유효성 검사
     */
    fun validateCompleteTrade(
        userId: String,
        status: String,
        sellerId: String,
        buyerId: String
    ) {
        // [1] status 검증
        if (status == ChatStatus.ENDED.name) {
            throw ItdaException(ErrorCode.ALREADY_ENDED_CHAT)
        }

        // [2] 권한 검증
        if (userId != sellerId && userId != buyerId) {
            throw ItdaException(ErrorCode.OTHERS_CHAT)
        }
    }

    /**
     * 거래취소 > 유효성 검사
     */
    fun validateCancelTrade(
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

        // [3] 취소 사유가 ETC이면 세부 사유를 필수로 입력해야 한다.
        if (request.cancelReason == TradeCancelReason.ETC && (request.content == null || request.content.isBlank())) {
            throw ItdaException(ErrorCode.NO_CONTENT_WHEN_REASON_IS_ETC)
        }
    }
}