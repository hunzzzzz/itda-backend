package com.moira.itda.domain.chat.temp.component

import com.moira.itda.global.entity.ChatStatus
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import org.springframework.stereotype.Component

@Component
class ChatValidator {
    /**
     * 거래완료 > 유효성 검사
     * 거래취소 > 유효성 검사
     */
    fun validate(
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
}