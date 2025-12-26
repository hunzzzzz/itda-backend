package com.moira.itda.domain.user.mychat.controller

import com.moira.itda.domain.user.mychat.dto.request.ChatMessageRequest
import com.moira.itda.domain.user.mychat.dto.request.TradeCancelRequest
import com.moira.itda.domain.user.mychat.dto.response.ChatMessageResponse
import com.moira.itda.domain.user.mychat.dto.response.MyChatPageResponse
import com.moira.itda.domain.user.mychat.dto.response.TradeSuggestResponse
import com.moira.itda.domain.user.mychat.service.MyChatService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
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

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 거래 제안 정보 조회
     */
    @GetMapping("/api/me/trade/chat/{chatRoomId}/trade")
    fun getTradeSuggest(@PathVariable chatRoomId: String): ResponseEntity<TradeSuggestResponse> {
        val response = myChatService.getTradeSuggest(chatRoomId = chatRoomId)

        return ResponseEntity.ok(response)
    }

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 이전 채팅 목록 조회
     */
    @GetMapping("/api/me/trade/chat/{chatRoomId}/messages")
    fun getChatMessageList(@PathVariable chatRoomId: String): ResponseEntity<List<ChatMessageResponse>> {
        val response = myChatService.getChatMessageList(chatRoomId = chatRoomId)

        return ResponseEntity.ok(response)
    }

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 메시지 전송
     */
    @MessageMapping("/chat/{chatRoomId}/message")
    fun sendMessage(
        @DestinationVariable chatRoomId: String,
        request: ChatMessageRequest
    ) {
        myChatService.sendMessage(chatRoomId = chatRoomId, request = request)
    }

    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회 > 채팅방 > 거래 취소
     */
    @PutMapping("/api/me/trade/chat/{chatRoomId}/cancel")
    fun cancelTrade(
        @PathVariable chatRoomId: String,
        @RequestBody request: TradeCancelRequest
    ) {
        myChatService.cancelTrade(chatRoomId = chatRoomId, request = request)
    }
}