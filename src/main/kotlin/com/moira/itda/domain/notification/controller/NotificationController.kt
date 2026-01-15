package com.moira.itda.domain.notification.controller

import com.moira.itda.domain.notification.dto.response.NotificationPageResponse
import com.moira.itda.domain.notification.service.NotificationService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 알림 모달
 */
@RestController
class NotificationController(
    private val service: NotificationService
) {
    /**
     * 알림목록 조회
     */
    @GetMapping("/api/notifications")
    fun getNotificationList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<NotificationPageResponse> {
        val response = service.getNotificationList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }

    /**
     * 알림 읽음처리
     */
    @PutMapping("/api/notifications/{notificationId}/read")
    fun readNotification(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable notificationId: Long
    ): ResponseEntity<Nothing?> {
        service.readNotification(userId = userAuth.userId, notificationId = notificationId)

        return ResponseEntity.ok().body(null)
    }

    /**
     * 읽은알림 삭제
     */
    @DeleteMapping("/api/notifications/read")
    fun deleteReadNotifications(@UserPrincipal userAuth: UserAuth): ResponseEntity<Nothing?> {
        service.deleteReadNotifications(userId = userAuth.userId)

        return ResponseEntity.ok().body(null)
    }
}