package com.moira.itda.domain.suggest_list.mapper

import com.moira.itda.global.entity.ChatRoom
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SuggestApproveMapper {
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