package com.moira.itda.domain.notification.component

import com.moira.itda.domain.notification.mapper.NotificationMapper
import com.moira.itda.global.entity.Notification
import com.moira.itda.global.entity.NotificationType
import org.slf4j.LoggerFactory
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class NotificationManager(
    private val messagingTemplate: SimpMessagingTemplate,
    private val mapper: NotificationMapper
) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    /**
     * 알림 전송 내부 메서드 호출을 위한 DTO 클래스
     */
    data class NotificationDto(
        val receiverId: String,
        val senderId: String,
        val type: NotificationType,
        val content: String,
        val targetId: String?
    )

    /**
     * [내부 메서드] 알림 전송
     */
    private fun send(dto: NotificationDto) {
        // [1] Notification 저장 (DB)
        val notification = Notification.from(
            receiverId = dto.receiverId,
            senderId = dto.senderId,
            type = dto.type,
            content = dto.content,
            targetId = dto.targetId
        )
        mapper.insertNotification(notification = notification)

        // [2] STOMP를 활용한 실시간 전송
        messagingTemplate.convertAndSendToUser(
            dto.receiverId, "/queue/notifications", notification
        )
    }

    /**
     * [내부 메서드] 에러 로그 출력
     */
    private fun errorLog() {
        log.error("[NotificationManager] 에러 발생! 알림 전송 과정 중 에러가 발생하였습니다.")
    }

    /**
     * 알림 전송 (구매제안/교환제안)
     */
    @Async
    fun sendSuggestNotification(senderId: String, tradeItemId: String) {
        // [1] 알림 전송을 위한 정보 조회
        val infoMap = mapper.selectSuggestNotificationInfo(senderId = senderId, tradeItemId = tradeItemId)

        val receiverId = infoMap["receiver_id"]
        val gachaTitle = infoMap["gacha_title"]
        val senderNickname = infoMap["sender_nickname"]
        val tradeTitle = infoMap["trade_title"]

        // [2] 알림 전송 메서드 호출
        if (receiverId != null && gachaTitle != null && senderNickname != null && tradeTitle != null) {
            val dto = NotificationDto(
                receiverId = receiverId,
                senderId = senderId,
                type = NotificationType.SUGGEST,
                content = "[${gachaTitle}]\n${senderNickname}님이 '${tradeTitle}'에 제안을 하였습니다.",
                targetId = tradeItemId
            )

            this.send(dto = dto)
        } else {
            this.errorLog()
        }
    }

    /**
     * 알림 전송 (제안 거절)
     */
    fun sendSuggestRejectedNotification(senderId: String, suggestId: String) {
        // [1] 알림 전송을 위한 정보 조회
        val infoMap = mapper.selectSuggestRejectNotificationInfo(senderId = senderId, suggestId = suggestId)

        val receiverId = infoMap["receiver_id"]
        val gachaTitle = infoMap["gacha_title"]
        val senderNickname = infoMap["sender_nickname"]
        val tradeTitle = infoMap["trade_title"]

        // [2] 알림 전송 메서드 호출
        if (receiverId != null && gachaTitle != null && senderNickname != null && tradeTitle != null) {
            val dto = NotificationDto(
                receiverId = receiverId,
                senderId = senderId,
                type = NotificationType.SUGGEST_REJECTED,
                content = "[${gachaTitle}]\n${senderNickname}님이 '${tradeTitle}'글의 내 제안을 거절하였습니다.",
                targetId = null // 알림을 받은 유저의 '내 활동' 페이지로 넘어가기 때문에 targetId 불필요
            )
            this.send(dto = dto)
        } else {
            this.errorLog()
        }
    }
}