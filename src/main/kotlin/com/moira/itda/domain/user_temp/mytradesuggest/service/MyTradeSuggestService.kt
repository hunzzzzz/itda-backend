package com.moira.itda.domain.user_temp.mytradesuggest.service

import com.moira.itda.domain.user_temp.mytradesuggest.dto.response.ChatRoomIdResponse
import com.moira.itda.domain.user_temp.mytradesuggest.dto.response.SuggestPageResponse
import com.moira.itda.domain.user_temp.mytradesuggest.mapper.MyTradeSuggestMapper
import com.moira.itda.global.entity.ChatRoom
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_TRADE_SUGGEST_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MyTradeSuggestService(
    private val myTradeSuggestMapper: MyTradeSuggestMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler
) {
    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 목록 조회
     */
    @Transactional(readOnly = true)
    fun getMyTradeSuggests(tradeId: String, page: Int): SuggestPageResponse {
        // [1] 변수 세팅
        val pageSize = MY_TRADE_SUGGEST_LIST_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 제안 목록 조회
        val totalElements = myTradeSuggestMapper.selectTradeSuggestListCnt(tradeId = tradeId)
        val contents = myTradeSuggestMapper.selectTradeSuggestList(
            tradeId = tradeId, pageSize = pageSize, offset = offset
        )

        // [3] 오프셋 페이지네이션 적용
        val pageResponse = offsetPaginationHandler.getPageResponse(
            pageSize = pageSize,
            page = page,
            totalElements = totalElements
        )

        // [4] DTO 병합 후 리턴
        return SuggestPageResponse(content = contents, page = pageResponse)
    }

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 승인
     */
    @Transactional
    fun approve(userId: String, tradeId: String, suggestId: String): ChatRoomIdResponse {
        // [1] 상태값 변경 (APPROVED)
        myTradeSuggestMapper.updateTradeSuggestStatusApproved(tradeId = tradeId, suggestId = suggestId)

        // [2] 제안 유저의 ID를 조회 (buyerId)
        val buyerId = myTradeSuggestMapper.selectTradeSuggestUserId(tradeId = tradeId, suggestId = suggestId)
            ?: throw ItdaException(ErrorCode.USER_NOT_FOUND)

        // [3] ChatRoom 저장
        val chatRoom = ChatRoom.from(
            tradeId = tradeId, tradeSuggestId = suggestId, sellerId = userId, buyerId = buyerId
        )
        myTradeSuggestMapper.insertChatRoom(chatRoom = chatRoom)

        // [4] 채팅방 ID 리턴
        return ChatRoomIdResponse(chatRoomId = chatRoom.id)
    }

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 거절
     */
    @Transactional
    fun reject(tradeId: String, suggestId: String) {
        // [1] 상태값 변경 (REJECTED)
        myTradeSuggestMapper.updateTradeSuggestStatusRejected(tradeId = tradeId, suggestId = suggestId)
    }
}