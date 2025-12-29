package com.moira.itda.domain.gacha.controller

import com.moira.itda.domain.gacha.dto.response.GachaDetailResponse
import com.moira.itda.domain.gacha.dto.response.GachaPageResponse
import com.moira.itda.domain.gacha.service.GachaService
import com.moira.itda.domain.gacha.dto.response.TargetPageResponse
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 가챠정보 > 가챠 목록
 * 가챠정보 > 가챠 목록 > 상세정보
 */
@RestController
class GachaController(
    private val service: GachaService
) {
    /**
     * 가챠정보 > 가챠 목록
     */
    @GetMapping("/api/gacha")
    fun getAll(
        @RequestParam(required = false, defaultValue = "") keyword: String,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "LATEST") sort: String
    ): ResponseEntity<GachaPageResponse> {
        val response = service.getGachaList(keyword = keyword, page = page, sort = sort)

        return ResponseEntity.ok(response)
    }

    /**
     * 가챠정보 > 가챠 목록 > 상세정보
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
     * 교환/판매 대상 지정 모달 > 가챠 목록
     */
    @GetMapping("/api/target/gacha")
    fun getGachaList(
        @RequestParam(required = false, defaultValue = "") keyword: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<TargetPageResponse> {
        val response = service.getTargetGachaList(keyword = keyword, page = page)

        return ResponseEntity.ok(response)
    }

    /**
     * 교환/판매 대상 지정 모달 > 즐겨찾기 가챠 목록
     */
    @GetMapping("/api/target/wish")
    fun getWishList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<TargetPageResponse> {
        val response = service.getTargetWishGachaList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }

    /**
     * 마이페이지 > 즐겨찾기 > 즐겨찾기 가챠 목록
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