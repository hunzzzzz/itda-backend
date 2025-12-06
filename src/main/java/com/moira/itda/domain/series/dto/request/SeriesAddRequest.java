package com.moira.itda.domain.series.dto.request;

import java.time.ZonedDateTime;
import java.util.List;

public record SeriesAddRequest(
        String title,
        String manufacturer,
        Integer price,
        String fileId,
        ZonedDateTime releasedAt,
        List<SeriesItemAddRequest> items
) {
}
