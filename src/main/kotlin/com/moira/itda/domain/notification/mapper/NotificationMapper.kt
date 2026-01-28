package com.moira.itda.domain.notification.mapper

import com.moira.itda.domain.notification.dto.response.NotificationResponse
import com.moira.itda.global.entity.Notification
import org.apache.ibatis.annotations.Mapper

@Mapper
interface NotificationMapper {
    /**
     * 알림 전송 > Notification 저장
     */
    fun insertNotification(notification: Notification)

    /**
     * 알림 목록 조회 > totalElements 계산
     */
    fun selectNotificationListCnt(userId: String): Long

    /**
     * 알림목록 조회
     */
    fun selectNotificationList(userId: String, pageSize: Int, offset: Int): List<NotificationResponse>

    /**
     * 알림 읽음처리
     */
    fun updateNotificationReadYn(userId: String, notificationId: Long)
    
    /**
     * 읽은알림 삭제
     */
    fun deleteReadNotificationList(userId: String)

    /**
     * 거래제안 알림 전송 > 거래제안 정보 조회
     */
    fun selectSuggestNotificationInfo(senderId: String, tradeItemId: String): HashMap<String, String?>

    /**
     * 제안거절 알림 전송 > 거래 정보 조회
     * 제안승인 알림 전송 > 거래 정보 조회
     */
    fun selectTradeNotificationInfo(senderId: String, suggestId: String): HashMap<String, String?>

    /**
     * 거래취소 알림 전송 > 채팅방 정보 조회
     */
    fun selectChatRoomNotificationInfo(senderId: String, chatRoomId: String): HashMap<String, String?>

    /**
     * 문의 답변 알림 전송 > 문의 정보 조회
     */
    fun sendSupportNotificationInfo(supportId: Long): HashMap<String, String?>
}