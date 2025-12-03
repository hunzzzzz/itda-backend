package com.moira.itda.domain.series.controller;

import com.moira.itda.domain.series.dto.request.SeriesAddRequest;
import com.moira.itda.domain.series.service.SeriesAdminService;
import com.moira.itda.global.aop.IsAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SeriesAdminController {
    private final SeriesAdminService seriesAdminService;

    /**
     * 가챠 시리즈 등록
     */
    @IsAdmin
    @PostMapping("/api/admin/series")
    public ResponseEntity<Object> add(@RequestBody SeriesAddRequest request) {
        seriesAdminService.add(request);

        return ResponseEntity.ok(null);
    }
}
