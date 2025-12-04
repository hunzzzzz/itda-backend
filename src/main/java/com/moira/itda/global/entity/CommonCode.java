package com.moira.itda.global.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class CommonCode {
    private String key;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
