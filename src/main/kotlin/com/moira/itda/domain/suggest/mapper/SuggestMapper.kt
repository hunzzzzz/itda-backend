package com.moira.itda.domain.suggest.mapper

import com.moira.itda.domain.suggest.dto.response.TradeSuggestResponse
import com.moira.itda.global.entity.ChatRoom
import com.moira.itda.global.entity.TradeSuggest
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

    /**
     * 거래 제안 모달 > 구매 제안 > 거래 status 조회
     * 거래 제안 모달 > 교환 제안 > 거래 status 조회
     */
    fun selectTradeStatus(tradeId: String): String

    /**
     * 거래 제안 모달 > 구매 제안 > 구매 제안 여부 확인
     */
    fun selectTradePurchaseSuggestChk(userId: String, tradeId: String, purchaseItemId: Long): Boolean

    /**
     * 거래 제안 모달 > 교환 제안 > 교환 제안 여부 확인
     */
    fun selectTradeExchangeSuggestChk(
        userId: String,
        tradeId: String,
        exchangeSellerItemId: Long,
        exchangeSuggestedItemId: Long
    ): Boolean

    /**
     * 거래 제안 모달 > 구매 제안 > TradeSuggest 저장
     * 거래 제안 모달 > 교환 제안 > TradeSuggest 저장
     */
    fun insertTradeSuggest(tradeSuggest: TradeSuggest)
}