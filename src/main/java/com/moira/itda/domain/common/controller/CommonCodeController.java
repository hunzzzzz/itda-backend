package com.moira.itda.domain.common.controller;

import com.moira.itda.domain.common.dto.request.CommonCodeAddRequest;
import com.moira.itda.domain.common.dto.response.CommonCodeDetailResponse;
import com.moira.itda.domain.common.dto.response.CommonCodeResponse;
import com.moira.itda.domain.common.service.CommonCodeService;
import com.moira.itda.global.aop.IsAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommonCodeController {
    private final CommonCodeService commonCodeService;

    /**
     * 공통코드 > 저장
     */
    @IsAdmin
    @PostMapping("/api/common/code")
    public ResponseEntity<Object> add(@RequestBody CommonCodeAddRequest request) {
        commonCodeService.add(request);

        return ResponseEntity.created(URI.create("/api/common/code")).build();
    }

    /**
     * 공통코드 > 단건 조회
     */
    @GetMapping("/api/common/code/{key}")
    public ResponseEntity<List<CommonCodeDetailResponse>> get(@PathVariable String key) {
        List<CommonCodeDetailResponse> list = commonCodeService.get(key);

        return ResponseEntity.ok(list);
    }

    /**
     * 공통코드 > 전체 조회
     */
    @IsAdmin
    @GetMapping("/api/common/code")
    public ResponseEntity<List<CommonCodeResponse>> getAll() {
        List<CommonCodeResponse> list = commonCodeService.getAll();

        return ResponseEntity.ok(list);
    }

    /**
     * 공통코드 > 삭제
     */
    @IsAdmin
    @DeleteMapping("/api/common/code/{key}")
    public ResponseEntity<Object> delete(@PathVariable String key) {
        commonCodeService.delete(key);

        return ResponseEntity.ok(null);
    }
}
