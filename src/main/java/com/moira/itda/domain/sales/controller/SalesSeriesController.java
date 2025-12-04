package com.moira.itda.domain.sales.controller;

import com.moira.itda.domain.sales.dto.response.SalesSeriesItemIdNameResponse;
import com.moira.itda.domain.sales.dto.response.SaleSeriesSearchResponse;
import com.moira.itda.domain.sales.service.SalesSeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SalesSeriesController {
    private final SalesSeriesService salesSeriesService;

    /**
     * 판매 > 판매등록 > 가챠 시리즈 목록 검색
     */
    @GetMapping("/api/sales/series")
    public ResponseEntity<SaleSeriesSearchResponse> getSeriesList(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "") String keyword
    ) {
        SaleSeriesSearchResponse saleSeriesSearchResponse = salesSeriesService.getSeriesList(page, keyword);

        return ResponseEntity.ok(saleSeriesSearchResponse);
    }

    /**
     * 판매 > 판매등록 > 가챠 시리즈 하위 아이템 목록 조회
     */
    @GetMapping("/api/sales/series/{seriesId}/items")
    public ResponseEntity<List<SalesSeriesItemIdNameResponse>> getSeriesItemList(
            @PathVariable String seriesId
    ) {
        List<SalesSeriesItemIdNameResponse> list = salesSeriesService.getSeriesItemList(seriesId);

        return ResponseEntity.ok(list);
    }
}
