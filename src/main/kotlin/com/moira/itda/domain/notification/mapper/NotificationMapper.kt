package com.moira.itda.domain.notification.mapper

import com.moira.itda.global.entity.Notification
import org.apache.ibatis.annotations.Mapper

@Mapper
interface NotificationMapper {
    /**
     * 알림 전송 > Notification 저장
     */
    fun insertNotification(notification: Notification)
}