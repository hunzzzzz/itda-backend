package com.moira.itda.domain.gacha_list.service

import com.moira.itda.domain.gacha_list.dto.response.GachaListPageResponse
import com.moira.itda.domain.gacha_list.mapper.GachaListMapper
import com.moira.itda.global.exception.ErrorCode
import com.moira.itda.global.exception.ItdaException
import com.moira.itda.global.pagination.component.OffsetPaginationHandler
import com.moira.itda.global.pagination.component.PageSizeConstant.Companion.GACHA_LIST_PAGE_SIZE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GachaListService(
    private val mapper: GachaListMapper,
    private val pageHandler: OffsetPaginationHandler
) {
    companion object {
        private val sortList = listOf(
            "LATEST",      // 최신순
            "OLDEST",      // 오래된순
            "MOST_VIEWED", // 조회수순
            "MOST_WISHED", // 높은 즐겨찾기순
            "MOST_TRADED"  // 높은 거래량순
        )
    }

    /**
     * 가챠목록 조회 > 유효성 검사
     */
    private fun validateQueryString(sort: String, showMyWish: String) {
        if (!sortList.contains(sort.uppercase())) {
            throw ItdaException(ErrorCode.INVALID_QUERY_PARAMETER)
        }
        if (showMyWish != "N" && showMyWish != "Y") {
            throw ItdaException(ErrorCode.INVALID_QUERY_PARAMETER)
        }
    }

    /**
     * 가챠목록 조회
     */
    @Transactional(readOnly = true)
    fun getGachaList(
        userId: String,
        keyword: String,
        page: Int,
        sort: String,
        showMyWish: String
    ): GachaListPageResponse {
        // [1] query string에 대한 유효성 검사
        this.validateQueryString(sort = sort, showMyWish = showMyWish)

        // [2] 변수 세팅
        val pageSize = GACHA_LIST_PAGE_SIZE
        val offset = pageHandler.getOffset(page = page, pageSize = pageSize)

        // [3] 조회
        val totalElements = mapper.selectGachaListCnt(
            userId = userId,
            keyword = keyword,
            showMyWish = showMyWish
        )
        val contents = mapper.selectGachaList(
            userId = userId,
            keyword = keyword,
            pageSize = pageSize,
            offset = offset,
            sort = sort,
            showMyWish = showMyWish
        )

        // [4] 오프셋 페이지네이션 구현
        val pageResponse = pageHandler.getPageResponse(
            page = page,
            pageSize = GACHA_LIST_PAGE_SIZE,
            totalElements = totalElements
        )

        // [5] DTO 병합 후 리턴
        return GachaListPageResponse(content = contents, page = pageResponse)
    }
}