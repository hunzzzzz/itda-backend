package com.moira.itda.domain.common_code.controller

import com.moira.itda.domain.common_code.dto.response.AllCodeDetailResponse
import com.moira.itda.domain.common_code.dto.response.CodeDetailResponse
import com.moira.itda.domain.common_code.service.CommonCodeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 공통코드 조회
 */
@RestController
class CommonCodeController(
    private val service: CommonCodeService
) {
    /**
     * 공통 > 공통코드 조회
     */
    @GetMapping("/api/code/{key}")
    fun getCode(@PathVariable key: String): ResponseEntity<List<CodeDetailResponse>> {
        val response = service.getCode(key = key)

        return ResponseEntity.ok(response)
    }

    /**
     * 공통 > 공통코드 일괄 조회
     */
    @GetMapping("/api/code/all")
    fun getAllCode(
        @RequestParam(required = true) code1: String,
        @RequestParam(required = false) code2: String?,
        @RequestParam(required = false) code3: String?,
        @RequestParam(required = false) code4: String?,
        @RequestParam(required = false) code5: String?,
    ): ResponseEntity<AllCodeDetailResponse> {
        val response = service.getAllCode(keyList = listOf(code1, code2, code3, code4, code5))

        return ResponseEntity.ok(response)
    }
}