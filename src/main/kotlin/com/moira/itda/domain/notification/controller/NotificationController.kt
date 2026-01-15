package com.moira.itda.domain.notification.controller

import com.moira.itda.domain.notification.dto.response.NotificationPageResponse
import com.moira.itda.domain.notification.service.NotificationService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
}