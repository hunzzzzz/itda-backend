package com.moira.itda.domain.suggest.mapper

import com.moira.itda.domain.suggest.dto.response.MyTradeSuggestResponse
import com.moira.itda.domain.suggest.dto.response.TradeSuggestResponse
import com.moira.itda.global.entity.ChatRoom
import com.moira.itda.global.entity.TradeSuggest
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SuggestMapper {
    /**
     * 거래제안 모달 > 구매제안 > TradeItem status 조회
     * 거래제안 모달 > 교환제안 > TradeItem status 조회
     */
    fun selectTradeItemStatus(tradeItemId: String): String

    /**
     * 거래제안 모달 > 구매제안 > 구매제안 여부 확인
     */
    fun selectTradeSuggestPurchaseChk(
        userId: String,
        tradeId: String,
        tradeItemId: String,
        purchaseItemId: Long
    ): Boolean

    /**
     * 거래제안 모달 > 교환제안 > 교환제안 여부 확인
     */
    fun selectTradeSuggestExchangeChk(
        userId: String,
        tradeId: String,
        tradeItemId: String,
        exchangeSellerItemId: Long,
        exchangeSuggestedItemId: Long
    ): Boolean

    /**
     * 거래제안 모달 > 구매제안 > TradeSuggest 저장
     * 거래제안 모달 > 교환제안 > TradeSuggest 저장
     */
    fun insertTradeSuggest(tradeSuggest: TradeSuggest)

    /**
     * 제안목록 조회 모달 > 제안목록 조회 > totalElements 계산
     */
    fun selectTradeSuggestListCnt(tradeId: String): Long

    /**
     * 제안목록 조회 모달 > 제안목록 조회
     */
    fun selectTradeSuggestList(
        tradeId: String,
        pageSize: Int,
        offset: Int
    ): List<TradeSuggestResponse>

    /**
     * 제안목록 조회 모달 > 제안승인 > TradeSuggest status 변경
     */
    fun updateTradeSuggestStatusApproved(suggestId: String)

    /**
     * 제안목록 조회 모달 > 제안승인 > ChatRoom 저장
     */
    fun insertChatRoom(chatRoom: ChatRoom)

    /**
     * 제안목록 조회 모달 > 제안거절 > TradeSuggest status 변경 (REJECTED)
     */
    fun updateTradeSuggestStatusRejected(suggestId: String)

    /**
     * 내 활동 > 제안 > 내 제안목록 조회 > totalElements 계산
     */
    fun selectMyTradeSuggestListCnt(userId: String): Long

    /**
     * 내 활동 > 제안 > 내 제안목록 조회
     */
    fun selectMyTradeSuggestList(
        userId: String,
        pageSize: Int,
        offset: Int
    ): List<MyTradeSuggestResponse>

    /**
     * 내 활동 > 제안 > 제안취소 > TradeSuggest 정보 조회
     */
    fun selectTradeSuggestInfo(suggestId: String): HashMap<String, String?>

    /**
     * 내 활동 > 제안 > 제안취소 > TradeSuggest status 변경 (CANCELED_BEFORE_RESPONSE)
     */
    fun updateTradeSuggestStatusCBR(suggestId: String)

    /**
     * 내 활동 > 제안 > 제안삭제 > TradeSuggest status 변경 (DELETED)
     */
    fun updateTradeSuggestStatusDeleted(suggestId: String)
}