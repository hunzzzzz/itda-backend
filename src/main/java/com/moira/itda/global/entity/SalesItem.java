package com.moira.itda.global.entity;

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
}
