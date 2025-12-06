package com.moira.itda.domain.series.controller;

import com.moira.itda.domain.series.dto.request.SeriesAddRequest;
import com.moira.itda.domain.series.dto.response.SeriesPageResponse;
import com.moira.itda.domain.series.service.SeriesService;
import com.moira.itda.global.aop.IsAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * 가챠시리즈 > 전체 조회
     */
    @GetMapping("/api/series")
    public ResponseEntity<SeriesPageResponse> getAll(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "1") int page
    ) {
        SeriesPageResponse list = seriesService.getAll(keyword, page);

        return ResponseEntity.ok(list);
    }
}
