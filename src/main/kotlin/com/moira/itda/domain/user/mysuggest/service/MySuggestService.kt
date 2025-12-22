package com.moira.itda.domain.user.mysuggest.service

import com.moira.itda.domain.user.mysuggest.dto.response.MySuggestPageResponse
import com.moira.itda.domain.user.mysuggest.mapper.MySuggestMapper
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.MY_TRADE_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MySuggestService(
    private val mySuggestMapper: MySuggestMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler
) {
    /**
     * 마이페이지 > 내 거래 목록 > 거래 제안 목록 탭 > 거래 제안 목록 조회
     */
    @Transactional(readOnly = true)
    fun getTradeSuggests(userId: String, page: Int): MySuggestPageResponse {
        // [1] 변수 세팅
        val pageSize = MY_TRADE_LIST_PAGE_SIZE
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = pageSize)

        // [2] 거래 정보 조회
        val totalElements = mySuggestMapper.selectTradeListCnt(userId = userId)
        val suggestList = mySuggestMapper.selectTradeList(
            userId = userId,
            pageSize = pageSize,
            offset = offset
        )

        // [3] 오프셋 페이지네이션 적용
        val pageResponse = offsetPaginationHandler.getPageResponse(
            page = page,
            pageSize = pageSize,
            totalElements = totalElements
        )

        // [4] DTO 병합 후 리턴
        return MySuggestPageResponse(suggest = suggestList, page = pageResponse)
    }
}