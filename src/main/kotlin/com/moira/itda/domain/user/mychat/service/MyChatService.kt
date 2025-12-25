package com.moira.itda.domain.user.mychat.service

import com.moira.itda.domain.user.mychat.dto.response.MyChatPageResponse
import com.moira.itda.domain.user.mychat.mapper.MyChatMapper
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_TRADE_CHAT_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MyChatService(
    private val myChatMapper: MyChatMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler
) {
    /**
     * 마이페이지 > 내 거래 목록 > 채팅 > 채팅 목록 조회
     */
    @Transactional(readOnly = true)
    fun getChatList(userId: String, page: Int): MyChatPageResponse {
        // [1] 변수 세팅
        val pageSize = MY_TRADE_CHAT_LIST_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 채팅방 정보 조회
        val totalElements = myChatMapper.selectChatRoomListCnt(userId = userId)
        val content = myChatMapper.selectChatRoomList(
            userId = userId, pageSize = pageSize, offset = offset
        )

        // [3] 오프셋 페이지네이션 구현
        val pageResponse = offsetPaginationHandler.getPageResponse(
            page = page, pageSize = pageSize, totalElements = totalElements
        )

        // [4] DTO 병합 후 리턴
        return MyChatPageResponse(content = content, page = pageResponse)
    }
}