package com.moira.itda.domain.sales.dto.response;

import com.moira.itda.global.page.OffsetPaginationInfo;

import java.util.List;

public record SaleSeriesSearchResponse(
        List<SaleSeriesResponse> contents,
        OffsetPaginationInfo pageInfo
) {
}
