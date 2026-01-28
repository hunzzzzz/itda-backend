package com.moira.itda.domain.gacha.pick.controller

import com.moira.itda.domain.gacha.pick.dto.request.PickRequest
import com.moira.itda.domain.gacha.pick.service.GachaPickService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GachaPickController(
    private val service: GachaPickService
) {
    /**
     * 가챠이력 저장
     */
    @PostMapping("/api/gacha/{gachaId}/pick")
    fun pick(
        @UserPrincipal userAuth: UserAuth,
        @PathVariable gachaId: String,
        @RequestBody request: PickRequest
    ): ResponseEntity<Nothing?> {
        service.pick(userId = userAuth.userId, gachaId = gachaId, request = request)

        return ResponseEntity.ok(null)
    }
}