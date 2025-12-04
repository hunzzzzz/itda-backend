package com.moira.itda.global.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class Sales {
    private String id;
    private String seriesId;
    private SalesStatus status;
    private String title;
    private String content;
    private String fileId;
    private SalesHopeMethod hopeMethod;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
