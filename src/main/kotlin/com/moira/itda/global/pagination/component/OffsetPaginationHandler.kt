package com.moira.itda.global.pagination.component

import com.moira.itda.global.pagination.dto.PageResponse
import org.springframework.stereotype.Component

@Component
class OffsetPaginationHandler {
    /**
     * 오프셋 계산
     */
    fun getOffset(page: Int, pageSize: Int): Int {
        return (page - 1) * pageSize
    }

    /**
     * 총 페이지 수 계산
     */
    fun getTotalPages(totalElements: Long, pageSize: Int): Long {
        return (totalElements / pageSize).coerceAtLeast(1)
    }

    /**
     * PageResponse 생성
     */
    fun getPageResponse(pageSize: Int, page: Int, totalElements: Long): PageResponse {
        return PageResponse(
            size = pageSize,
            number = page,
            totalElements = totalElements,
            totalPages = this.getTotalPages(totalElements = totalElements, pageSize = pageSize)
        )
    }
}