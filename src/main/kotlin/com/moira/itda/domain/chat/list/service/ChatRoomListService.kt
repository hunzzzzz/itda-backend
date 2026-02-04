package com.moira.itda.domain.chat.list.service

import com.moira.itda.domain.chat.list.dto.response.ChatRoomPageResponse
import com.moira.itda.domain.chat.list.mapper.ChatRoomListMapper
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_CHAT_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomListService(
    private val mapper: ChatRoomListMapper,
    private val pageHandler: OffsetPaginationHandler
) {
    /**
     * 내 채팅방 목록 조회
     */
    @Transactional(readOnly = true)
    fun getChatRoomList(userId: String, page: Int): ChatRoomPageResponse {
        // [1] 변수 세팅
        val pageSize = MY_CHAT_LIST_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 채팅방 정보 조회
        val totalElements = mapper.selectChatRoomListCnt(userId = userId)
        val content = mapper.selectChatRoomList(
            userId = userId, pageSize = pageSize, offset = offset
        )

        // [3] 오프셋 페이지네이션 구현
        val pageResponse = pageHandler.getPageResponse(
            page = page, pageSize = pageSize, totalElements = totalElements
        )

        // [4] DTO 병합 후 리턴
        return ChatRoomPageResponse(content = content, page = pageResponse)
    }
}