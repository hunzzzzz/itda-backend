package com.moira.itda.domain.suggest.service

import com.moira.itda.domain.suggest.mapper.SuggestMapper
import com.moira.itda.domain.suggest.dto.response.TradeSuggestPageResponse
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_TRADE_SUGGEST_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SuggestService(
    private val offsetPaginationHandler: OffsetPaginationHandler,
    private val mapper: SuggestMapper
) {
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
}