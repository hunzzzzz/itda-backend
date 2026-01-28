package com.moira.itda.domain.gacha.common.controller

import com.moira.itda.domain.gacha.common.dto.response.GachaItemNameResponse
import com.moira.itda.domain.gacha.common.service.GachaCommonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GachaCommonController(
    private val service: GachaCommonService
) {
    /**
     * 가챠이력 저장 모달 > 가챠이력 저장 > 가챠 아이템목록 조회
     * 거래제안 모달 > 가챠 아이템목록 조회
     * 교환등록 > 가챠 아이템목록 조회
     * 판매등록 > 가챠 아이템목록 조회
     */
    @GetMapping("/api/gacha/{gachaId}/items")
    fun getGachaItemList(@PathVariable gachaId: String): ResponseEntity<List<GachaItemNameResponse>> {
        val response = service.getGachaItemList(gachaId = gachaId)

        return ResponseEntity.ok(response)
    }
}