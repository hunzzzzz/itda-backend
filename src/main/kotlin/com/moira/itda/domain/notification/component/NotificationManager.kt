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
     * 알림 전송 전, 거래 및 제안 정보를 추출하기 위한 DTO 클래스
     */
    data class NotificationTradeSourceDto(
        val receiverId: String,
        val senderNickname: String,
        val gachaTitle: String,
        val tradeTitle: String
    )

    /**
     * 알림 전송 전, 유저 피드백 정보를 추출하기 위한 DTO 클래스
     */
    data class NotificationFeedbackSourceDto(
        val receiverId: String,
        val feedbackTitle: String
    )

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
     * [내부 메서드] 거래 및 제안 정보 조회 및 추출
     */
    private fun extractTradeInfo(infoMap: HashMap<String, String?>): NotificationTradeSourceDto? {
        val receiverId = infoMap["receiver_id"]
        val senderNickname = infoMap["sender_nickname"]
        val gachaTitle = infoMap["gacha_title"]
        val tradeTitle = infoMap["trade_title"]

        if (receiverId != null && gachaTitle != null && senderNickname != null && tradeTitle != null) {
            return NotificationTradeSourceDto(
                receiverId = receiverId,
                senderNickname = senderNickname,
                gachaTitle = gachaTitle,
                tradeTitle = tradeTitle
            )
        } else {
            this.errorLog()
        }

        return null
    }

    /**
     * [내부 메서드] 유저 피드백 정보 조회 및 추출
     */
    private fun extractFeedbackInfo(infoMap: HashMap<String, String?>): NotificationFeedbackSourceDto? {
        val receiverId = infoMap["receiver_id"]
        val feedbackTitle = infoMap["feedback_title"]

        if (receiverId != null && feedbackTitle != null) {
            return NotificationFeedbackSourceDto(
                receiverId = receiverId,
                feedbackTitle = feedbackTitle
            )
        } else {
            this.errorLog()
        }

        return null
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
    fun sendSuggestNotification(senderId: String, tradeId: String, tradeItemId: String) {
        // [1] 알림 전송을 위한 정보 조회
        val infoMap = mapper.selectSuggestNotificationInfo(senderId = senderId, tradeItemId = tradeItemId)
        val sourceDto = this.extractTradeInfo(infoMap)

        // [2] 알림 전송 메서드 호출
        sourceDto?.let {
            NotificationDto(
                receiverId = it.receiverId,
                senderId = senderId,
                type = NotificationType.SUGGEST,
                content = "[${it.gachaTitle}]\n${it.senderNickname}님이 '${it.tradeTitle}'에 제안을 하였습니다.",
                targetId = tradeId
            )
        }?.let { this.send(dto = it) }
    }

    /**
     * 알림 전송 (제안 거절)
     */
    @Async
    fun sendSuggestRejectedNotification(senderId: String, suggestId: String) {
        // [1] 알림 전송을 위한 정보 조회
        val infoMap = mapper.selectSuggestReactInfo(senderId = senderId, suggestId = suggestId)
        val sourceDto = this.extractTradeInfo(infoMap)

        // [2] 알림 전송 메서드 호출
        sourceDto?.let {
            NotificationDto(
                receiverId = it.receiverId,
                senderId = senderId,
                type = NotificationType.SUGGEST_REJECTED,
                content = "[${it.gachaTitle}]\n${it.senderNickname}님이 '${it.tradeTitle}'글의 내 제안을 거절하였습니다.",
                targetId = null // 알림을 받은 유저의 '내 활동' 페이지로 넘어가기 때문에 targetId 불필요
            )
        }?.let { this.send(dto = it) }
    }

    /**
     * 알림 전송 (제안 거절)
     */
    @Async
    fun sendSuggestApproveNotification(senderId: String, suggestId: String, chatRoomId: String) {
        // [1] 알림 전송을 위한 정보 조회
        val infoMap = mapper.selectSuggestReactInfo(senderId = senderId, suggestId = suggestId)
        val sourceDto = this.extractTradeInfo(infoMap)

        // [2] 알림 전송 메서드 호출
        sourceDto?.let {
            NotificationDto(
                receiverId = it.receiverId,
                senderId = senderId,
                type = NotificationType.SUGGEST_APPROVED,
                content = "[${it.gachaTitle}]\n${it.senderNickname}님이 '${it.tradeTitle}'글의 내 제안을 수락하셨습니다. 지금 대화를 시작해보세요!",
                targetId = chatRoomId // 채팅방 ID
            )
        }?.let { this.send(dto = it) }
    }

    /**
     * 알림 전송 (피드백 답변)
     */
    @Async
    fun sendFeedbackAnswerNotification(senderId: String, feedbackId: Long) {
        // [1] 알림 전송을 위한 정보 조회
        val infoMap = mapper.selectFeedbackInfo(feedbackId = feedbackId)
        val sourceDto = this.extractFeedbackInfo(infoMap)

        // [2] 알림 전송 메서드 호출
        sourceDto?.let {
            NotificationDto(
                receiverId = it.receiverId,
                senderId = senderId,
                type = NotificationType.FEEDBACK_ANSWERED,
                content = "[문의사항 답변]\n'${it.feedbackTitle}' 문의사항에 대한 관리자의 답변이 등록되었습니다.",
                targetId = null // 알림을 받은 유저의 '문의 목록' 페이지로 넘어가기 때문에 targetId 불필요
            )
        }?.let { this.send(dto = it) }
    }
}