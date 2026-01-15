package com.moira.itda.domain.notification.service

import com.moira.itda.domain.notification.dto.response.NotificationPageResponse
import com.moira.itda.domain.notification.mapper.NotificationMapper
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.NOTIFICATION_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationService(
    private val mapper: NotificationMapper,
    private val pageHandler: OffsetPaginationHandler
) {
    /**
     * 알림목록 조회
     */
    @Transactional(readOnly = true)
    fun getNotificationList(userId: String, page: Int): NotificationPageResponse {
        // [1] 변수 세팅
        val pageSize = NOTIFICATION_LIST_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 조회
        val totalElements = mapper.selectNotificationListCnt(userId = userId)
        val contents = mapper.selectNotificationList(userId = userId, pageSize = pageSize, offset = offset)

        // [3] 오프셋 페이지네이션 적용
        val pageResponse = pageHandler.getPageResponse(page = page, pageSize = pageSize, totalElements = totalElements)

        // [4] DTO 병합 후 리턴
        return NotificationPageResponse(content = contents, page = pageResponse)
    }

    /**
     * 알림 읽음처리
     */
    @Transactional
    fun readNotification(userId: String, notificationId: Long) {
        // [1] readYn을 Y로 수정
        mapper.updateNotificationReadYn(userId = userId, notificationId = notificationId)
    }

    /**
     * 읽은알림 삭제
     */
    @Transactional
    fun deleteReadNotifications(userId: String) {
        // [1] 읽은알림 삭제
        mapper.deleteReadNotificationList(userId = userId)
    }
}