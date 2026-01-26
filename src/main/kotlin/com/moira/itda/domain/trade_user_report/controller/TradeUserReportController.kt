package com.moira.itda.domain.trade_user_report.controller

import com.moira.itda.domain.chat_room.dto.response.ChatRoomIdResponse
import com.moira.itda.domain.trade_user_report.dto.request.ReportRequest
import com.moira.itda.domain.trade_user_report.service.TradeUserReportService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 유저신고 모달
 */
@RestController
class TradeUserReportController(
    private val service: TradeUserReportService
) {
    /**
     * 유저신고
     */
    @PostMapping("/api/trade/chat/{chatRoomId}/report")
    fun report(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable chatRoomId: String,
        @RequestBody request: ReportRequest
    ): ResponseEntity<ChatRoomIdResponse> {
        service.report(userId = userAuth.userId, chatRoomId = chatRoomId, request = request)

        return ResponseEntity.ok().body(ChatRoomIdResponse(chatRoomId = chatRoomId))
    }
}