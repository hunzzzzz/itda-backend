package com.moira.itda.domain.series.dto.request;

import com.moira.itda.global.entity.GachaSeries;

import java.time.ZonedDateTime;
import java.util.List;

public record SeriesAddRequest(
        String title,
        String manufacturer,
        Integer price,
        String fileId,
        ZonedDateTime releasedAt,
        List<ItemAddRequest> items
) {
    public GachaSeries toGachaSeries(String seriesId) {
        return GachaSeries.builder()
                .id(seriesId)
                .title(this.title)
                .manufacturer(this.manufacturer)
                .price(this.price)
                .fileId(this.fileId)
                .releasedAt(this.releasedAt)
                .createdAt(ZonedDateTime.now())
                .build();
    }
}
