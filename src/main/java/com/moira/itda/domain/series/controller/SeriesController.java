package com.moira.itda.domain.series.controller;

import com.moira.itda.domain.series.dto.request.SeriesAddRequest;
import com.moira.itda.domain.series.dto.response.SeriesDetailResponse;
import com.moira.itda.domain.series.dto.response.SeriesPageResponse;
import com.moira.itda.domain.series.service.SeriesService;
import com.moira.itda.global.aop.IsAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SeriesController {
    private final SeriesService seriesService;

    /**
     * 가챠시리즈 > 등록
     */
    @IsAdmin
    @PostMapping("/api/series")
    public ResponseEntity<Object> add(@RequestBody SeriesAddRequest request) {
        seriesService.add(request);

        return ResponseEntity.ok(null);
    }

    /**
     * 가챠시리즈 > 전체 조회 (오프셋 기반 페이지네이션)
     */
    @GetMapping("/api/series")
    public ResponseEntity<SeriesPageResponse> getAll(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "1") int page
    ) {
        SeriesPageResponse list = seriesService.get(keyword, page);

        return ResponseEntity.ok(list);
    }

    /**
     * 가챠시리즈 > 단건 조회
     */
    @GetMapping("/api/series/{seriesId}")
    public ResponseEntity<List<SeriesDetailResponse>> get(
            @PathVariable String seriesId
    ) {
        List<SeriesDetailResponse> list = seriesService.get(seriesId);

        return ResponseEntity.ok(list);
    }
}
