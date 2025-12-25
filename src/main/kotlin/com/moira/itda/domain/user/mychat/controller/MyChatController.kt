package com.moira.itda.domain.user.mychat.controller

import com.moira.itda.domain.user.mychat.dto.response.MyChatPageResponse
import com.moira.itda.domain.user.mychat.service.MyChatService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 마이페이지 > 내 거래 목록 조회 > 채팅
 */
@RestController
class MyChatController(
    private val myChatService: MyChatService
) {
    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회
     */
    @GetMapping("/api/me/trade/chat")
    fun getChatList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<MyChatPageResponse?> {
        val response = myChatService.getChatList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }
}