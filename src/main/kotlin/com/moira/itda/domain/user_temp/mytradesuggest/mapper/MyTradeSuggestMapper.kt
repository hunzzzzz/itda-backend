package com.moira.itda.domain.user_temp.mytradesuggest.mapper

import com.moira.itda.global.entity.ChatRoom
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MyTradeSuggestMapper {
    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 승인 > status 변경 (APPROVED)
     */
    fun updateTradeSuggestStatusApproved(tradeId: String, suggestId: String)

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 승인 > 제안한 유저의 ID 조회
     */
    fun selectTradeSuggestUserId(tradeId: String, suggestId: String): String?

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 승인 > ChatRoom 저장
     */
    fun insertChatRoom(chatRoom: ChatRoom)

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 거절 > status 변경 (REJECTED)
     */
    fun updateTradeSuggestStatusRejected(tradeId: String, suggestId: String)
}