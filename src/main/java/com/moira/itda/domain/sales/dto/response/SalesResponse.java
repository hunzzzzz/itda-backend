package com.moira.itda.domain.sales.dto.response;

import java.time.ZonedDateTime;

public record SalesResponse(
        String id,
        String seriesId,
        String userId,
        String status,
        String title,
        String fileUrl,
        String hopeMethod,
        ZonedDateTime createdAt
) {
}
