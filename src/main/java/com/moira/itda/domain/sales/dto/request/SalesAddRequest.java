package com.moira.itda.domain.sales.dto.request;

import java.util.List;

public record SalesAddRequest(
        String title,
        String content,
        String fileId,
        String hopeMethod,
        List<SalesItemAddRequest> items
) {
}
