package com.moira.itda.domain.admin.support.service

import com.moira.itda.domain.admin.support.dto.request.AnswerRequest
import com.moira.itda.domain.admin.support.dto.response.SupportResponse
import com.moira.itda.domain.admin.support.mapper.AdminSupportMapper
import com.moira.itda.domain.notification.component.NotificationManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminSupportService(
    private val mapper: AdminSupportMapper,
    private val notificationManager: NotificationManager
) {
    /**
     * 문의 목록 조회
     */
    @Transactional(readOnly = true)
    fun getUserSupportList(): List<SupportResponse> {
        return mapper.selectUserSupportList()
    }

    /**
     * 문의 답변
     */
    @Transactional
    fun answer(userId: String, supportId: Long, request: AnswerRequest) {
        // [1] UserSupport 수정
        mapper.updateUserSupportAnswerContent(supportId = supportId, answerContent = request.content)

        // [2] 알림 전송 (비동기)
        notificationManager.sendUserSupportAnswerNotification(senderId = userId, supportId = supportId)
    }
}