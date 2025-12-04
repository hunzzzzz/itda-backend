package com.moira.itda.global.page;

import org.springframework.stereotype.Component;

@Component
public class PaginationHandler {
    /**
     * 총 페이지 개수 계산
     */
    private int getTotalPages(Long totalCount, int pageSize) {
        return (int) Math.ceil((double) totalCount / pageSize);
    }

    /**
     * OffsetPaginationInfo 객체 획득
     */
    public OffsetPaginationInfo getOffsetPaginationInfo(
            Long totalCount, int pageNum, int pageSize
    ) {
        return new OffsetPaginationInfo(
                totalCount, // 총 개수
                pageNum,    // 현재 페이지 번호
                pageSize,   // 페이지 크기
                this.getTotalPages(totalCount, pageSize) // 총 페이지 개수
        );
    }
}
