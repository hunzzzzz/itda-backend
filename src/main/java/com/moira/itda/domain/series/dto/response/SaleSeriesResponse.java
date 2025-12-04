package com.moira.itda.domain.series.dto.response;

import com.moira.itda.global.page.OffsetPaginationInfo;

import java.util.List;

public record SaleSeriesResponse(
        List<SaleSeriesContentsResponse> contents,
        OffsetPaginationInfo pageInfo
) {
}
