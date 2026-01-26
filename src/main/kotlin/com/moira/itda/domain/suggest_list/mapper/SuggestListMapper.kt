package com.moira.itda.domain.suggest_list.mapper

import com.moira.itda.domain.suggest_list.dto.response.SuggestListResponse
import com.moira.itda.global.entity.ChatRoom
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SuggestListMapper {
    /**
     * 제안목록 조회 > totalElements 계산
     */
    fun selectTradeSuggestListCnt(tradeId: String): Long

    /**
     * 제안목록 조회
     */
    fun selectTradeSuggestList(
        userId: String,
        tradeId: String,
        pageSize: Int,
        offset: Int
    ): List<SuggestListResponse>

    /**
     * 제안승인 > TradeSuggest status 변경 (APPROVED)
     */
    fun updateTradeSuggestStatusApproved(suggestId: String)

    /**
     * 제안승인 > ChatRoom 저장
     */
    fun insertChatRoom(chatRoom: ChatRoom)

    /**
     * 제안거절 > TradeSuggest status 변경 (REJECTED)
     */
    fun updateTradeSuggestStatusRejected(suggestId: String)
}