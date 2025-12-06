package com.moira.itda.global.entity;

import com.moira.itda.domain.series.dto.request.SeriesItemAddRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class GachaItem {
    private Long id;
    private String seriesId;
    private String name;
    private ZonedDateTime createdAt;

    public static GachaItem fromSeriesItemAddRequest(String seriesId, SeriesItemAddRequest request) {
        return GachaItem.builder()
                .seriesId(seriesId)
                .name(request.name())
                .createdAt(ZonedDateTime.now())
                .build();
    }
}
