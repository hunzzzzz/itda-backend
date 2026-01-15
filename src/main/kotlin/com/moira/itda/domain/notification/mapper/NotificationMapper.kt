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
     * 알림 목록 조회
     */
    fun selectNotificationList(userId: String, pageSize: Int, offset: Int): List<NotificationResponse>

    /**
     * 거래제안 알림 전송 > 거래제안 알림 정보 조회
     */
    fun selectSuggestNotificationInfo(senderId: String, tradeItemId: String): HashMap<String, String?>
}