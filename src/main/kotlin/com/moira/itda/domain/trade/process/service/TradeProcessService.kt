package com.moira.itda.domain.trade.process.service

import com.moira.itda.domain.trade.process.dto.response.ProcessPageResponse
import com.moira.itda.domain.trade.process.mapper.TradeProcessMapper
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_PROCESSING_TRADE_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TradeProcessService(
    private val mapper: TradeProcessMapper,
    private val pageHandler: OffsetPaginationHandler
) {
    /**
     * 진행 중인 거래목록 조회
     */
    @Transactional(readOnly = true)
    fun getTradeProcessList(userId: String, page: Int): ProcessPageResponse {
        // [1] 변수 세팅
        val pageSize = MY_PROCESSING_TRADE_LIST_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 조회
        val totalElements = mapper.selectTradeProcessResponseListCnt(userId = userId)
        val contents = mapper.selectTradeProcessResponseList(
            userId = userId, pageSize = pageSize, offset = offset
        )

        // [3] 오프셋 페이지네이션 구현
        val pageResponse = pageHandler.getPageResponse(
            pageSize = pageSize,
            page = page,
            totalElements = totalElements,
        )

        // [4] DTO 병합 후 리턴
        return ProcessPageResponse(content = contents, page = pageResponse)


    }
}