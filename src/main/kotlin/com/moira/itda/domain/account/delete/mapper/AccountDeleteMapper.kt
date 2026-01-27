package com.moira.itda.domain.account.delete.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface AccountDeleteMapper {
    /**
     * 회원탈퇴 > User status 변경 (DELETED)
     */
    fun updateUserStatusDeleted(userId: String)

    /**
     * 회윈털퇴 > PENDING인 Trade 존재 여부 확인
     */
    fun selectPendingTradeChk(userId: String): Boolean

    /**
     * 회원탈퇴 > PENDING인 TradeSuggest 존재 여부 확인
     */
    fun selectPendingTradeSuggestChk(userId: String): Boolean

    /**
     * 회원탈퇴 > ACTIVE인 ChatRoom 존재 여부 확인
     */
    fun selectActiveChatRoomChk(userId: String): Boolean
}