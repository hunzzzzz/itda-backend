package com.moira.itda.domain.gacha_pick.controller

import com.moira.itda.domain.gacha_pick.dto.request.GachaPickRequest
import com.moira.itda.domain.gacha_pick.service.GachaPickService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 가챠정보 > 가챠 목록 > 상세정보 > 가챠 이력 저장
 */
@RestController
class GachaPickController(
    private val service: GachaPickService
) {
    /**
     * 가챠정보 > 가챠 목록 > 상세정보 > 가챠 이력 저장
     */
    @PostMapping("/api/gacha/{gachaId}/pick")
    fun pick(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable gachaId: String,
        @RequestBody request: GachaPickRequest
    ): ResponseEntity<Nothing?> {
        service.pick(userId = userAuth.userId, gachaId = gachaId, request = request)

        return ResponseEntity.ok(null)
    }
}