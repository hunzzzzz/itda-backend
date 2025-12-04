package com.moira.itda.global.page;

public record OffsetPaginationInfo(
        Long totalCount,
        int pageNum,
        int pageSize,
        int totalPages
) {
}
