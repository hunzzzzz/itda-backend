package com.moira.itda.global.entity;

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
}
