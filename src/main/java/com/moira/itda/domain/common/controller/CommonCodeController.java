package com.moira.itda.domain.common.controller;

import com.moira.itda.domain.common.dto.CommonCodeResponse;
import com.moira.itda.domain.common.service.CommonCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommonCodeController {
    private final CommonCodeService commonCodeService;

    @GetMapping("/api/common")
    public ResponseEntity<List<CommonCodeResponse>> getCommonCode(@RequestParam String key) {
        List<CommonCodeResponse> list = commonCodeService.getCommonCode(key);

        return ResponseEntity.ok(list);
    }
}
