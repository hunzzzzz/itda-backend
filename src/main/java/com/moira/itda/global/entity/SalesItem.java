package com.moira.itda.global.entity;

import com.moira.itda.domain.sales.dto.request.SalesItemAddRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class SalesItem {
    private Long id;
    private String salesId;
    private String seriesId;
    private Long seriesItemId;
    private int count;
    private int currentCount;
    private int price;
    private ZonedDateTime createdAt;

    public static SalesItem fromSalesItemAddRequest(String seriesId, String salesId, SalesItemAddRequest request) {
        return SalesItem.builder()
                .salesId(salesId)
                .seriesId(seriesId)
                .seriesItemId(request.seriesItemId())
                .count(request.count())
                .currentCount(request.count())
                .price(request.price())
                .createdAt(ZonedDateTime.now())
                .build();
    }
}
