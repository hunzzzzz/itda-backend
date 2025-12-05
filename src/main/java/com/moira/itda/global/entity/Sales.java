package com.moira.itda.global.entity;

import com.moira.itda.domain.sales.dto.request.SalesAddRequest;
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
    private String userId;
    private SalesStatus status;
    private String title;
    private String content;
    private String fileId;
    private SalesHopeMethod hopeMethod;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static Sales fromSalesAddRequest(String salesId, String seriesId, String userId, SalesAddRequest request) {
        return Sales.builder()
                .id(salesId)
                .seriesId(seriesId)
                .userId(userId)
                .status(SalesStatus.PENDING)
                .title(request.title())
                .content(request.content())
                .fileId(request.fileId())
                .hopeMethod(SalesHopeMethod.valueOf(request.hopeMethod()))
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
    }
}
