package com.moira.itda.domain.series.dto.request;

import com.moira.itda.global.entity.GachaItem;

import java.time.ZonedDateTime;

public record ItemAddRequest(
        String name
) {
    public GachaItem toGachaItem(String seriesId) {
        return GachaItem.builder()
                .seriesId(seriesId)
                .name(this.name)
                .createdAt(ZonedDateTime.now())
                .build();
    }
}
