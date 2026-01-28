package com.moira.itda.domain.gacha.detail.controller

import com.moira.itda.domain.gacha.detail.dto.response.GachaDetailResponse
import com.moira.itda.domain.gacha.detail.service.GachaDetailService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GachaDetailController(
    private val service: GachaDetailService
) {
    /**
     * 가챠상세정보
     */
    @GetMapping("/api/gacha/{gachaId}")
    fun get(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable gachaId: String,
        httpReq: HttpServletRequest,
        httpRes: HttpServletResponse
    ): ResponseEntity<GachaDetailResponse> {
        val response = service.getGacha(
            userId = userAuth.userId,
            gachaId = gachaId,
            httpReq = httpReq,
            httpRes = httpRes
        )

        return ResponseEntity.ok(response)
    }

    /**
     * 가챠상세정보 > 즐겨찾기
     */
    @PutMapping("/api/gacha/{gachaId}/wish")
    fun wish(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable gachaId: String
    ): ResponseEntity<Nothing?> {
        service.wish(userId = userAuth.userId, gachaId = gachaId)

        return ResponseEntity.ok(null)
    }
}