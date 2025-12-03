package com.moira.itda.global.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
public class LoginHistory {
    private Long id;
    private String userId;
    private String successYn;
    private String ipAddress;
    private String userAgent;
    private ZonedDateTime loginAt;
}
