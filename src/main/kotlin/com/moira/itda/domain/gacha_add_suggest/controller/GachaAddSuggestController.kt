package com.moira.itda.domain.gacha_add_suggest.controller

import com.moira.itda.domain.gacha_add_suggest.dto.request.GachaAddSuggestRequest
import com.moira.itda.domain.gacha_add_suggest.dto.response.AdminGachaAddSuggestResponse
import com.moira.itda.domain.gacha_add_suggest.dto.response.MyGachaAddSuggestPageResponse
import com.moira.itda.domain.gacha_add_suggest.service.GachaAddSuggestService
import com.moira.itda.global.auth.aop.IsAdmin
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 정보등록요청
 */
@RestController
class GachaAddSuggestController(
    private val service: GachaAddSuggestService
) {
    /**
     * 정보등록요청
     */
    @PostMapping("/api/gacha/add/suggest")
    fun add(
        @UserPrincipal userAuth: UserAuth,
        @RequestBody request: GachaAddSuggestRequest
    ): ResponseEntity<Nothing> {
        service.add(userId = userAuth.userId, request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }

    /**
     * 마이페이지 > 정보등록요청 결과
     */
    @GetMapping("/api/me/gacha/add/suggest")
    fun getAll(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<MyGachaAddSuggestPageResponse> {
        val response = service.getAll(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }

    /**
     * 어드민 > 정보등록요청
     */
    @IsAdmin
    @GetMapping("/api/admin/gacha/add/suggest")
    fun getAll(): ResponseEntity<List<AdminGachaAddSuggestResponse>> {
        val response = service.getAll()

        return ResponseEntity.ok(response)
    }
}