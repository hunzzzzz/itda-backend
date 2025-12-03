package com.moira.itda.global.entity;

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
}
