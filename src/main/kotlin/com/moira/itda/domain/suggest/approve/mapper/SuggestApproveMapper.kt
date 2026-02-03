package com.moira.itda.domain.suggest.approve.mapper

import com.moira.itda.global.entity.ChatRoom
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SuggestApproveMapper {
    /**
     * 제안승인 > TradeSuggest의 status 변경 (APPROVED)
     */
    fun updateTradeSuggestStatusApproved(suggestId: String)

    /**
     * 제안승인 > ChatRoom 저장
     */
    fun insertChatRoom(chatRoom: ChatRoom)
}