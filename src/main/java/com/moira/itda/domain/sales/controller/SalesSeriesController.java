package com.moira.itda.domain.sales.controller;

import com.moira.itda.domain.sales.dto.request.SalesAddRequest;
import com.moira.itda.domain.sales.dto.response.SaleSeriesSearchResponse;
import com.moira.itda.domain.sales.dto.response.SalesSeriesItemIdNameResponse;
import com.moira.itda.domain.sales.service.SalesSeriesService;
import com.moira.itda.global.aop.UserPrincipal;
import com.moira.itda.global.auth.SimpleUserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 판매 > 판매등록
     */
    @PostMapping("/api/sales/series/{seriesId}")
    public ResponseEntity<Object> addSales(
            @UserPrincipal SimpleUserAuth userAuth,
            @PathVariable String seriesId,
            @RequestBody SalesAddRequest request
    ) {
        salesSeriesService.addSales(userAuth, seriesId, request);

        return ResponseEntity.ok(null);
    }
}
