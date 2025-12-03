package com.moira.itda.global.entity;

import com.moira.itda.domain.series.dto.request.SeriesAddRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class GachaSeries {
    private String id;
    private String title;
    private String manufacturer;
    private String fileId;
    private Integer price;
    private Long viewCount;
    private ZonedDateTime releasedAt;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static GachaSeries fromSeriesAddRequest(String seriesId, SeriesAddRequest request) {
        return GachaSeries.builder()
                .id(seriesId)
                .title(request.title())
                .manufacturer(request.manufacturer())
                .fileId(request.fileId())
                .price(request.price())
                .viewCount(0L)
                .releasedAt(request.releasedAt())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
    }
}
