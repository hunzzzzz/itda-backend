package com.moira.itda.domain.gacha_wish.controller

import com.moira.itda.domain.gacha_wish.service.GachaWishService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GachaWishController(
    private val service: GachaWishService
) {
    /**
     * 가챠정보 > 가챠목록 > 상세정보 > 즐겨찾기
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