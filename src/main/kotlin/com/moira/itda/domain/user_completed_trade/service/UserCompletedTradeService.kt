package com.moira.itda.domain.user_completed_trade.service

import com.moira.itda.domain.user_completed_trade.dto.response.CompletedTradePageResponse
import com.moira.itda.domain.user_completed_trade.mapper.UserCompletedTradeMapper
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_COMPLETED_TRADE_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserCompletedTradeService(
    private val mapper: UserCompletedTradeMapper,
    private val pageHandler: OffsetPaginationHandler
) {
    /**
     * 완료된 거래목록 조회
     */
    @Transactional(readOnly = true)
    fun getCompletedTradeList(userId: String, page: Int): CompletedTradePageResponse {
        // [1] 변수 세팅
        val pageSize = MY_COMPLETED_TRADE_LIST_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [2] totalElements 계산
        val totalElements = mapper.selectCompletedTradeListCnt(userId = userId)

        // [3] 조회
        val contents = mapper.selectCompletedTradeList(userId = userId)
        val pageResponse = pageHandler.getPageResponse(pageSize = pageSize, page = page, totalElements = totalElements)

        // [4] DTO 병합 후 리턴
        return CompletedTradePageResponse(content = contents, page = pageResponse)


    }
}