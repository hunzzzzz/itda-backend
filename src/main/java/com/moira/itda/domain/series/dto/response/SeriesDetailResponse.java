package com.moira.itda.domain.series.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeriesDetailResponse {
    private String seriesId;
    private String title;
    private String manufacturer;
    private String fileId;
    private String fileUrl;
    private int price;
    private Long viewCount;
    private ZonedDateTime releasedAt;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private List<SeriesItemResponse> items;
}
