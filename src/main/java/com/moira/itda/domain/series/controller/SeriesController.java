package com.moira.itda.domain.series.controller;

import com.moira.itda.domain.series.dto.response.SaleSeriesResponse;
import com.moira.itda.domain.series.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SeriesController {
    private final SeriesService seriesService;

    /**
     * 판매 > 판매등록 > 가챠 시리즈 목록 검색
     */
    @GetMapping("/api/sales/series")
    public ResponseEntity<SaleSeriesResponse> getSeriesList(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "") String keyword
    ) {
        SaleSeriesResponse list = seriesService.getSeriesList(page, keyword);

        return ResponseEntity.ok(list);
    }
}
