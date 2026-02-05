package com.moira.itda.domain.user_activity_completed_trade.service

import com.moira.itda.domain.user_activity_completed_trade.dto.request.ComplimentRequest
import com.moira.itda.domain.user_activity_completed_trade.dto.response.CompletedTradePageResponse
import com.moira.itda.domain.user_activity_completed_trade.mapper.UserActivityCompletedTradeMapper
import com.moira.itda.global.entity.TradeUserCompliment
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_PROCESSING_TRADE_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserActivityCompletedTradeService(
    private val mapper: UserActivityCompletedTradeMapper,
    private val pageHandler: OffsetPaginationHandler
) {
    /**
     * 완료된 거래목록 조회
     */
    @Transactional(readOnly = true)
    fun getCompletedTradeList(userId: String, page: Int): CompletedTradePageResponse {
        // [1] 변수 세팅
        val pageSize = MY_PROCESSING_TRADE_LIST_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [2] totalElements 계산
        val totalElements = mapper.selectCompletedTradeListCnt(userId = userId)

        // [3] 조회
        val contents = mapper.selectCompletedTradeList(userId = userId)
        val pageResponse = pageHandler.getPageResponse(pageSize = pageSize, page = page, totalElements = totalElements)

        // [4] DTO 병합 후 리턴
        return CompletedTradePageResponse(content = contents, page = pageResponse)
    }

    /**
     * 유저칭찬
     */
    @Transactional
    fun compliment(userId: String, request: ComplimentRequest) {
        // [1] 유효성 검사 (특정 거래(TradeSuggest 기준)당 한 번의 칭찬만 가능)
        if (mapper.selectTradeUserComplimentChk(userId = userId, tradeSuggestId = request.tradeSuggestId)) {
            throw ItdaException(ErrorCode.ALREADY_COMPLIMENTED)
        }

        // [2] TradeUserCompliment 저장
        val tradeUserCompliment = TradeUserCompliment.from(userId = userId, request = request)
        mapper.insertTradeUserCompliment(tradeUserCompliment = tradeUserCompliment)
    }
}