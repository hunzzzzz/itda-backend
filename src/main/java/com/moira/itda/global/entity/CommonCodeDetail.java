package com.moira.itda.global.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class CommonCodeDetail {
    private Long id;
    private String key;
    private String korName;
    private String engName;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
