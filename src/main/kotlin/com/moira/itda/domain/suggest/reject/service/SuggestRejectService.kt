package com.moira.itda.domain.suggest.reject.service

import com.moira.itda.domain.notification.component.NotificationManager
import com.moira.itda.domain.suggest.reject.mapper.SuggestRejectMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SuggestRejectService(
    private val mapper: SuggestRejectMapper,
    private val notificationManager: NotificationManager
) {
    /**
     * 제안거절
     */
    @Transactional
    fun reject(userId: String, suggestId: String) {
        // [1] TradeSuggest의 상태값을 REJECTED로 변경
        mapper.updateTradeSuggestStatusRejected(suggestId = suggestId)

        // [2] 알림 전송 (비동기)
        notificationManager.sendSuggestRejectedNotification(senderId = userId, suggestId = suggestId)
    }
}