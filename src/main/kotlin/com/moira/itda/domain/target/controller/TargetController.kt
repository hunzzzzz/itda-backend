package com.moira.itda.domain.target.controller

import com.moira.itda.domain.target.dto.response.TargetPageResponse
import com.moira.itda.domain.target.service.TargetService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 교환 및 판매 대상 지정 모달
 */
@RestController
class TargetController(
    private val targetService: TargetService
) {
    /**
     * 교환 및 판매 대상 지정 모달 > 즐겨찾기 목록
     */
    @GetMapping("/api/target/wish")
    fun getWishList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<TargetPageResponse> {
        val response = targetService.getWishList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }

    /**
     * 교환 및 판매 대상 지정 모달 > 가챠 목록
     */
    @GetMapping("/api/target/gacha")
    fun getGachaList(
        @RequestParam(required = false, defaultValue = "") keyword: String,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<TargetPageResponse> {
        val response = targetService.getGachaList(keyword = keyword, page = page)

        return ResponseEntity.ok(response)
    }
}