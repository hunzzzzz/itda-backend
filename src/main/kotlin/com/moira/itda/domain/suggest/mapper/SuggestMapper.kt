package com.moira.itda.domain.suggest.mapper

import com.moira.itda.domain.suggest.dto.response.TradeSuggestResponse
import com.moira.itda.global.entity.ChatRoom
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SuggestMapper {
    /**
     * 내 활동 > 판매/교환 > 제안 목록 조회 모달 > 제안 목록 조회 > totalElements 계산
     */
    fun selectTradeSuggestListCnt(tradeId: String): Long

    /**
     * 내 활동 > 판매/교환 > 제안 목록 조회 모달 > 제안 목록 조회
     */
    fun selectTradeSuggestList(tradeId: String, pageSize: Int, offset: Int): List<TradeSuggestResponse>

    /**
     * 내 활동 > 판매/교환 > 제안 목록 조회 모달 > 제안 승인 > TradeSuggest status 변경
     */
    fun updateTradeSuggestStatusApproved(suggestId: String)

    /**
     * 내 활동 > 판매/교환 > 제안 목록 조회 모달 > 제안 승인 > ChatRoom 저장
     */
    fun insertChatRoom(chatRoom: ChatRoom)

    /**
     * 내 활동 > 판매/교환 > 제안 목록 조회 모달 > 제안 거절 > TradeSuggest status 변경
     */
    fun updateTradeSuggestStatusRejected(suggestId: String)
}