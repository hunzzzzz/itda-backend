package com.moira.itda.domain.gacha_add_suggest.controller

import com.moira.itda.domain.gacha_add_suggest.dto.request.GachaAddSuggestRequest
import com.moira.itda.domain.gacha_add_suggest.service.GachaAddSuggestService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 정보등록요청
 */
@RestController
class GachaAddSuggestController(
    private val gachaAddSuggestService: GachaAddSuggestService
) {
    /**
     * 정보등록요청
     */
    @PostMapping("/api/gacha/suggest/add")
    fun add(
        @UserPrincipal userAuth: UserAuth,
        @RequestBody request: GachaAddSuggestRequest
    ): ResponseEntity<Nothing> {
        gachaAddSuggestService.add(userId = userAuth.userId, request = request)

        return ResponseEntity.status(HttpStatus.CREATED).body(null)
    }
}