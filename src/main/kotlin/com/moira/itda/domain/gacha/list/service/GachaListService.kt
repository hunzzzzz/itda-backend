package com.moira.itda.domain.gacha.list.service

import com.moira.itda.domain.gacha.list.component.GachaListSortCondition
import com.moira.itda.domain.gacha.list.dto.response.GachaPageResponse
import com.moira.itda.domain.gacha.list.mapper.GachaListMapper
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.GACHA_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GachaListService(
    private val gachaListMapper: GachaListMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler
) {
    private fun validateSortCondition(sort: String) {
        kotlin.runCatching {
            GachaListSortCondition.valueOf(sort)
        }.onFailure {
            throw ItdaException(ErrorCode.INVALID_SORT_CONDITION)
        }
    }

    /**
     * 가챠정보 > 가챠목록 > 전체 목록 조회
     */
    @Transactional(readOnly = true)
    fun getAll(keyword: String, page: Int, sort: String): GachaPageResponse {
        // [1] 정렬 조건에 대한 유효성 검사
        this.validateSortCondition(sort = sort)

        // [2] 변수 세팅
        val keywordPattern = if (keyword.isBlank()) "" else "%${keyword}%"
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = GACHA_LIST_PAGE_SIZE)

        // [3] 조회
        val totalElements = gachaListMapper.selectGachaCnt(keywordPattern = keywordPattern)
        val contents = gachaListMapper.selectGachaList(
            keywordPattern = keywordPattern,
            pageSize = GACHA_LIST_PAGE_SIZE,
            offset = offset,
            sort = sort
        )

        // [4] 오프셋 페이지네이션 구현
        val pageResponse = offsetPaginationHandler.getPageResponse(
            page = page,
            pageSize = GACHA_LIST_PAGE_SIZE,
            totalElements = totalElements
        )

        // [5] DTO 병합 후 리턴
        return GachaPageResponse(content = contents, page = pageResponse)
    }
}