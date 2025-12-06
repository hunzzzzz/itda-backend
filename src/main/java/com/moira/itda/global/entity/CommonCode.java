package com.moira.itda.global.entity;

import com.moira.itda.domain.common.dto.request.CommonCodeAddRequest;
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

    public static CommonCode fromCommonCodeAddRequest(CommonCodeAddRequest request) {
        return CommonCode.builder()
                .key(request.codeKey())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
    }
}
