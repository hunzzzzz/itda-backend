package com.moira.itda.domain.gacha.service

import com.moira.itda.domain.gacha.component.GachaValidator
import com.moira.itda.domain.gacha.dto.response.GachaPageResponse
import com.moira.itda.domain.gacha.mapper.GachaMapper
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.GACHA_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GachaService(
    private val mapper: GachaMapper,
    private val offsetPaginationHandler: OffsetPaginationHandler,
    private val validator: GachaValidator,
) {
    /**
     * 가챠정보 > 가챠 목록
     */
    @Transactional(readOnly = true)
    fun getAll(keyword: String, page: Int, sort: String): GachaPageResponse {
        // [1] 정렬 조건에 대한 유효성 검사
        validator.validateSort(sort = sort)

        // [2] 변수 세팅
        val offset = offsetPaginationHandler.getOffset(page = page, pageSize = GACHA_LIST_PAGE_SIZE)

        // [3] 조회
        val totalElements = mapper.selectGachaListCnt(keyword = keyword)
        val contents = mapper.selectGachaList(
            keyword = keyword,
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