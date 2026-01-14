package com.moira.itda.domain.user_gacha_wish_list.controller

import com.moira.itda.domain.user_gacha_wish_list.dto.response.WishGachaPageResponse
import com.moira.itda.domain.user_gacha_wish_list.service.UserGachaWishService
import com.moira.itda.global.auth.aop.UserPrincipal
import com.moira.itda.global.auth.dto.UserAuth
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserGachaWishListController(
    private val service: UserGachaWishService
) {
    /**
     * 즐겨찾기 가챠목록 조회
     */
    @GetMapping("/api/me/wish")
    fun getWishGachaList(
        @UserPrincipal userAuth: UserAuth,
        @RequestParam(required = false, defaultValue = "1") page: Int
    ): ResponseEntity<WishGachaPageResponse> {
        val response = service.getWishGachaList(userId = userAuth.userId, page = page)

        return ResponseEntity.ok(response)
    }
}