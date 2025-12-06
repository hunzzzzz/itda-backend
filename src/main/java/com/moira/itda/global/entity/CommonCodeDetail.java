package com.moira.itda.global.entity;

import com.moira.itda.domain.common.dto.request.CommonCodeDetailAddRequest;
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

    public static CommonCodeDetail fromCommonCodeDetailAddRequest(String key, CommonCodeDetailAddRequest request) {
        return CommonCodeDetail.builder()
                .key(key)
                .korName(request.korName())
                .engName(request.engName())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
    }
}
