package com.moira.itda.domain.user.mytradesuggest.mapper

import com.moira.itda.domain.user.mytradesuggest.dto.response.ExchangeSuggestResponse
import com.moira.itda.domain.user.mytradesuggest.dto.response.PurchaseSuggestResponse
import com.moira.itda.global.entity.ChatMessage
import com.moira.itda.global.entity.ChatRoom
import org.apache.ibatis.annotations.Mapper

@Mapper
interface MyTradeSuggestMapper {
    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 목록 조회 > 거래 type 조회
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 승인 > 거래 type 조회
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 거절 > 거래 type 조회
     */
    fun selectTradeType(tradeId: String): String?

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 목록 조회 > 구매 제안 목록 조회 > totalElements 계산
     */
    fun selectTradePurchaseSuggestListCnt(tradeId: String): Long

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 목록 조회 > 구매 제안 목록 조회
     */
    fun selectTradePurchaseSuggestList(
        tradeId: String,
        pageSize: Int,
        offset: Int
    ): List<PurchaseSuggestResponse>

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 목록 조회 > 교환 제안 목록 조회 > totalElements 계산
     */
    fun selectTradeExchangeSuggestListCnt(tradeId: String): Long

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 목록 조회 > 교환 제안 목록 조회
     */
    fun selectTradeExchangeSuggestList(
        tradeId: String,
        pageSize: Int,
        offset: Int
    ): List<ExchangeSuggestResponse>

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 승인 > 구매 제안 승인
     */
    fun updateTradePurchaseSuggestStatusApproved(tradeId: String, suggestId: Long)

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 승인 > 교환 제안 승인
     */
    fun updateTradeExchangeSuggestStatusApproved(tradeId: String, suggestId: Long)

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 승인 > 구매 제안 유저 ID 조회
     */
    fun selectTradePurchaseSuggestUserId(tradeId: String, suggestId: Long): String?

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 승인 > 교환 제안 유저 ID 조회
     */
    fun selectTradeExchangeSuggestUserId(tradeId: String, suggestId: Long): String?

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 승인 > ChatRoom 저장
     */
    fun insertChatRoom(chatRoom: ChatRoom)

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 승인 > ChatMessage 저장
     */
    fun insertChatMessage(chatMessage: ChatMessage)

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 거절 > 구매 제안 거절
     */
    fun updateTradePurchaseSuggestStatusRejected(tradeId: String, suggestId: Long)

    /**
     * 마이페이지 > 내 거래 목록 조회 > 제안 목록 모달 > 제안 거절 > 교환 제안 거절
     */
    fun updateTradeExchangeSuggestStatusRejected(tradeId: String, suggestId: Long)
}