package com.moira.itda.domain.notification.component

import com.moira.itda.domain.notification.mapper.NotificationMapper
import com.moira.itda.global.entity.Notification
import com.moira.itda.global.entity.NotificationType
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class NotificationManager(
    private val messagingTemplate: SimpMessagingTemplate,
    private val mapper: NotificationMapper
) {
    /**
     * 알림 전송
     */
    @Transactional
    fun send(receiverId: String, senderId: String, type: NotificationType, content: String, targetId: String) {
        // [1] Notification 저장 (DB)
        val notification = Notification.from(
            receiverId = receiverId,
            senderId = senderId,
            type = type,
            content = content,
            targetId = targetId
        )
        mapper.insertNotification(notification = notification)

        // [2] STOMP를 활용한 실시간 전송
        messagingTemplate.convertAndSendToUser(
            receiverId, "/queue/notifications", notification
        )
    }
}