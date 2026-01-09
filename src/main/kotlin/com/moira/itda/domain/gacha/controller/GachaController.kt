package com.moira.itda.domain.gacha.controller

import com.moira.itda.domain.gacha.dto.response.GachaDetailResponse
import com.moira.itda.domain.gacha.dto.response.GachaPageResponse
import com.moira.itda.domain.gacha.service.GachaService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class GachaController(
    private val service: GachaService
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

    /**
     * 마이페이지 > 즐겨찾기 > 즐겨찾기 가챠목록
     */
    @GetMapping("/api/me/wish")
    fun getWishGachaList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<GachaPageResponse> {
        val response = service.getWishGachaList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }
}