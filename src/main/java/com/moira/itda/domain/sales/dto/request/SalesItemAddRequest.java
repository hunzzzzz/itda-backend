package com.moira.itda.domain.sales.dto.request;

public record SalesItemAddRequest(
        Long seriesItemId,
        int count,
        int price
) {
}
