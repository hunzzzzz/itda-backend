package com.moira.itda.domain.suggest.service

import com.moira.itda.domain.suggest.component.SuggestValidator
import com.moira.itda.domain.suggest.dto.request.ExchangeSuggestRequest
import com.moira.itda.domain.suggest.dto.request.PurchaseSuggestRequest
import com.moira.itda.domain.suggest.dto.request.TradeSuggestYnRequest
import com.moira.itda.domain.suggest.dto.response.ChatRoomIdResponse
import com.moira.itda.domain.suggest.dto.response.TradeSuggestPageResponse
import com.moira.itda.domain.suggest.mapper.SuggestMapper
import com.moira.itda.global.entity.ChatRoom
import com.moira.itda.global.entity.TradeSuggest
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_TRADE_SUGGEST_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SuggestService(
    private val mapper: SuggestMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler,
    private val validator: SuggestValidator
) {
    /**
     * 거래 제안 모달 > 구매 제안
     */
    @Transactional
    fun purchaseSuggest(userId: String, tradeId: String, request: PurchaseSuggestRequest) {
        // [1] 유효성 검사
        validator.validatePurchaseSuggest(userId = userId, tradeId = tradeId, request = request)

        // [2] TradeSuggest 저장
        val tradeSuggest = TradeSuggest.fromPurchaseSuggestRequest(
            userId = userId,
            tradeId = tradeId,
            request = request
        )

        mapper.insertTradeSuggest(tradeSuggest = tradeSuggest)
    }

    /**
     * 거래 제안 모달 > 교환 제안
     */
    fun exchangeSuggest(userId: String, tradeId: String, request: ExchangeSuggestRequest) {
        // [1] 유효성 검사
        validator.validateExchangeSuggest(userId = userId, tradeId = tradeId, request = request)

        // [2] TradeSuggest 저장
        val tradeSuggest = TradeSuggest.fromExchangeSuggestRequest(
            userId = userId,
            tradeId = tradeId,
            request = request
        )
        mapper.insertTradeSuggest(tradeSuggest = tradeSuggest)
    }

    /**
     * 내 활동 > 판매/교환 > 제안 목록 조회 모달 > 제안 목록 조회
     */
    @Transactional(readOnly = true)
    fun getSuggestList(tradeId: String, page: Int): TradeSuggestPageResponse {
        // [1] 변수 세팅
        val pageSize = MY_TRADE_SUGGEST_LIST_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 제안 목록 조회
        val totalElements = mapper.selectTradeSuggestListCnt(tradeId = tradeId)
        val content = mapper.selectTradeSuggestList(
            tradeId = tradeId, pageSize = pageSize, offset = offset
        )

        // [3] 오프셋 페이지네이션 적용
        val pageResponse = offsetPaginationHandler.getPageResponse(
            pageSize = pageSize,
            page = page,
            totalElements = totalElements
        )

        // [4] DTO 병합 후 리턴
        return TradeSuggestPageResponse(content = content, page = pageResponse)
    }

    /**
     * 내 활동 > 판매/교환 > 제안 목록 조회 모달 > 제안 승인
     */
    @Transactional
    fun approve(userId: String, tradeId: String, request: TradeSuggestYnRequest): ChatRoomIdResponse {
        // [1] 변수 세팅
        val tradeItemId = request.tradeItemId
        val tradeSuggestId = request.tradeSuggestId
        val buyerId = request.userId

        // [2] TradeSuggest의 상태값을 APPROVED로 변경
        mapper.updateTradeSuggestStatusApproved(suggestId = tradeSuggestId)

        // [3] ChatRoom 저장
        val chatRoom = ChatRoom.from(
            tradeId = tradeId,
            tradeItemId = tradeItemId,
            tradeSuggestId = tradeSuggestId,
            sellerId = userId,
            buyerId = buyerId
        )
        mapper.insertChatRoom(chatRoom = chatRoom)

        // [4] 채팅방 ID 리턴
        return ChatRoomIdResponse(chatRoomId = chatRoom.id)
    }

    /**
     * 내 활동 > 판매/교환 > 제안 목록 조회 모달 > 제안 거절
     */
    @Transactional
    fun reject(tradeId: String, request: TradeSuggestYnRequest) {
        // [1] 변수 세팅
        val suggestId = request.tradeSuggestId

        // [2] TradeSuggest의 상태값을 REJECTED로 변경
        mapper.updateTradeSuggestStatusRejected(suggestId = suggestId)
    }
}