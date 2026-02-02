package com.moira.itda.domain.suggest.list.service

import com.moira.itda.domain.suggest.list.dto.response.SuggestListPageResponse
import com.moira.itda.domain.suggest.list.mapper.SuggestListMapper
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_SUGGEST_LIST_PAGE_SIZE
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.TRADE_SUGGEST_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SuggestListService(
    private val mapper: SuggestListMapper,
    private val pageHandler: OffsetPaginationHandler,
) {
    /**
     * 제안목록 조회
     */
    @Transactional(readOnly = true)
    fun getSuggestList(userId: String, tradeId: String, page: Int): SuggestListPageResponse {
        // [1] 변수 세팅
        val pageSize = TRADE_SUGGEST_LIST_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 제안목록 조회
        val totalElements = mapper.selectTradeSuggestListCnt(tradeId = tradeId)
        val content = mapper.selectTradeSuggestList(
            userId = userId,
            tradeId = tradeId,
            pageSize = pageSize,
            offset = offset
        )

        // [3] 오프셋 페이지네이션 적용
        val pageResponse = pageHandler.getPageResponse(
            pageSize = pageSize,
            page = page,
            totalElements = totalElements
        )

        // [4] DTO 병합 후 리턴
        return SuggestListPageResponse(content = content, page = pageResponse)
    }

    /**
     * 내 활동 > 제안 > 내 제안목록 조회
     */
    @Transactional(readOnly = true)
    fun getMySuggestList(userId: String, page: Int): SuggestListPageResponse {
        // [1] 변수 세팅
        val pageSize = MY_SUGGEST_LIST_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 거래 정보 조회
        val totalElements = mapper.selectMyTradeSuggestListCnt(userId = userId)
        val content = mapper.selectMyTradeSuggestList(
            userId = userId,
            pageSize = pageSize,
            offset = offset
        )

        // [3] 오프셋 페이지네이션 적용
        val pageResponse = pageHandler.getPageResponse(
            page = page,
            pageSize = pageSize,
            totalElements = totalElements
        )

        // [4] DTO 병합 후 리턴
        return SuggestListPageResponse(content = content, page = pageResponse)
    }

}